/* (c) https://github.com/MontiCore/monticore */
package automata2cd;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._visitor.AutomataVisitor2;
import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;
import de.monticore.cdbasis._ast.ASTCDCompilationUnitBuilder;
import de.monticore.cdbasis._ast.ASTCDDefinition;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.umlmodifier.UMLModifierMill;
import de.se_rwth.commons.Splitters;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Automata2CDStateVisitor implements AutomataVisitor2 {
  
  public final static String ERROR_CODE = "0xDC012";
  
  protected ASTAutomaton astAutomaton;
  
  protected ASTCDCompilationUnit cdCompilationUnit;

  protected ASTCDClass automataClass;
  /**
   * The super class for all state implementations
   */
  protected ASTCDClass stateSuperClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> stateToClassMap = new HashMap<>();
  /**
   * Code template reference
   */
  protected final CD4C cd4C;
  
  protected final GlobalExtensionManagement glex;
  
  /**
   * Name of the initial state
   */
  protected String initialState = "";
  
  public Automata2CDStateVisitor(GlobalExtensionManagement glex) {
    this.cd4C = CD4C.getInstance();
    this.glex = glex;
  }
  
  
  @Override
  public void visit(ASTAutomaton astAutomaton) {
    this.astAutomaton = astAutomaton;
  
    // Add a CDDefinition for every automaton
    ASTCDDefinition astcdDefinition = CDBasisMill.cDDefinitionBuilder().setName(astAutomaton.getName())
      .setModifier(UMLModifierMill.modifierBuilder().build()).build();
  
    ASTCDCompilationUnitBuilder cdCompilationUnitBuilder = CDBasisMill.cDCompilationUnitBuilder();
    cdCompilationUnitBuilder.setCDDefinition(astcdDefinition);
  
    cdCompilationUnit = cdCompilationUnitBuilder.build();
  
    // Main class, names equally to the Automaton
    automataClass = CDBasisMill.cDClassBuilder().setName(astAutomaton.getName())
      .setModifier(CDBasisMill.modifierBuilder().setPublic(true).build()).build();
    astcdDefinition.addCDElement(automataClass);
    
    // replace the template to add a setState method
    this.cd4C.addMethod(this.automataClass, "automaton2cd.StateSetStateMethod");
  }
  
  @Override
  public void endVisit(ASTAutomaton astAutomaton) {
    // Generate the constructor of the class
    // It inits all state attributes
    // and the initial state
    this.cd4C.addConstructor(this.automataClass, "automaton2cd.StateInitConstructor",
      automataClass.getName(),
      this.stateToClassMap.keySet(),
      this.initialState);
  }
  
  @Override
  public void visit(ASTState state) {
    if (state.getName().equals("state")) {
      Log.error(ERROR_CODE + "State is named \"state\", which interferes with the attribute for the currently selected state");
    }
    if (initialState.isEmpty()) {
      initialState = state.getName();
    }
    
    // A class extending StateClass for this state
    ASTCDClass stateClass = CDBasisMill.cDClassBuilder().setName(state.getName())
      .setModifier(CDBasisMill.modifierBuilder().build())
      .setCDExtendUsage(CDBasisMill.cDExtendUsageBuilder().addSuperclass(qualifiedType("StateClass")).build())
      .build();
    // Add the StateClassImpl to the CD and mapping
    this.cdCompilationUnit.getCDDefinition().addCDElement(stateClass);
    this.stateToClassMap.put(state.getName(), stateClass);
    
    // Add reference to this in the main class, in form of an attribute
    cd4C.addAttribute(automataClass, "protected " + state.getName() + " " + StringUtils.uncapitalize(state.getName()) + ";");
    
    // Set the initial state
    if (state.isInitial()) {
      this.initialState = state.getName();
    }
  }
  
  public ASTCDCompilationUnit getCdCompilationUnit() {
    return cdCompilationUnit;
  }
  
  public ASTCDClass getScClass() {
    return automataClass;
  }
  
  public Map<String, ASTCDClass> getStateToClassMap() {
    return stateToClassMap;
  }
  
  public ASTCDClass getStateSuperClass() {
    return stateSuperClass;
  }
  
  // Support methods
  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }
  
  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
      .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }

}
