/* (c) https://github.com/MontiCore/monticore */
package automata2cd;

import automata._ast.ASTTransition;
import automata._visitor.AutomataVisitor2;
import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cd4code.prettyprint.CD4CodeFullPrettyPrinter;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.types.mcbasictypes._ast.ASTMCReturnType;
import de.se_rwth.commons.Splitters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Automata2CDTransitionVisitor implements AutomataVisitor2 {
  
  /**
   * The main class
   */
  protected final ASTCDClass automataClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> transitionToClassMap;
  
  protected final ASTMCReturnType voidReturnType;
  protected final CD4CodeFullPrettyPrinter cd4CodeFullPrettyPrinter;
  
  protected final ASTCDClass transitionSuperClass;
  protected final CD4C cd4C;
  
  
  protected final List<String> stimuli = new ArrayList<>();
  
  
  public Automata2CDTransitionVisitor(ASTCDClass automataClass,
                                Map<String, ASTCDClass> transitionToClassMap,
                                ASTCDClass transitionSuperClass) {
    this.automataClass = automataClass;
    this.transitionToClassMap = transitionToClassMap;
    this.transitionSuperClass = transitionSuperClass;
    this.cd4C = CD4C.getInstance();
    
    this.cd4CodeFullPrettyPrinter = new CD4CodeFullPrettyPrinter(new IndentPrinter());
    this.voidReturnType = CDBasisMill.mCReturnTypeBuilder().setMCVoidType(CDBasisMill.mCVoidTypeBuilder().build()).build();
  }
  
  
  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }
  
  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
      .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }
  
  
  protected Optional<ASTTransition> transition = Optional.empty();
  
  @Override
  public void visit(ASTTransition node) {
    this.transition = Optional.of(node);
  }
  
  @Override
  public void endVisit(ASTTransition node) {
    this.transition = Optional.empty();
  }

}
