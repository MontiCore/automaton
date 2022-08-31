/* (c) https://github.com/MontiCore/monticore */
package automata2cd;

import automata._ast.ASTTransition;
import automata._visitor.AutomataVisitor2;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;

public class Automata2CDUMLTransitionVisitor extends Automata2CDTransitionVisitor implements AutomataVisitor2 {
  
  public Automata2CDUMLTransitionVisitor(ASTCDClass automataClass,
                                         Map<String, ASTCDClass> transitionToClassMap,
                                         ASTCDClass transitionSuperClass) {
    super(automataClass, transitionToClassMap, transitionSuperClass);
  }
  
  @Override
  public void visit(ASTTransition node) {
    this.transition = Optional.of(node);
    String stimulus = node.getInput();
    
    if (!stimuli.contains(stimulus)) {
      
      // Add stimulus method to the Class
      cd4C.addMethod(automataClass, "automaton2cd.StateStimulusMethod", stimulus, automataClass.getName());
      
      // Add handleStimulus(Class k) method to the StateClass
      transitionSuperClass.addCDMember(CD4CodeMill.cDMethodBuilder()
        .setModifier(CDBasisMill.modifierBuilder().PUBLIC().ABSTRACT().build())
        .setMCReturnType(voidReturnType)
        .setName("handle" + StringUtils.capitalize(stimulus)).addCDParameter(
          CD4CodeMill.cDParameterBuilder().setName("k").setMCType(qualifiedType(automataClass.getName())).build())
        .build());
      
      this.stimuli.add(stimulus);
    }
    
    if (!transition.isPresent()) return;
    
    // Add handleStimulus(Class k) method to the source-state StateClass impl
    if (!this.transitionToClassMap.containsKey(this.transition.get().getFrom())) {
      throw new IllegalStateException("No source state " + this.transition.get().getFrom() + " found!");
    }
    ASTCDClass stateImplClass = this.transitionToClassMap.get(this.transition.get().getFrom());
    if (stateImplClass.getCDMethodList().stream()
      .anyMatch(x -> x.getName().equals("handle" + StringUtils.capitalize(stimulus)))) {
      // This might occur due to stimuli with arguments
      throw new IllegalStateException("Duplicate transition " + stimulus + " in " + this.transition.get().getFrom() + " found!");
    }

    // Finally, add the method
    cd4C.addMethod(stateImplClass, "automaton2cd.StateClassHandleStimulus", stimulus, automataClass.getName(),
      transition.get().getTo());
  }
}
