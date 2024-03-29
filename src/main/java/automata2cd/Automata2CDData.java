/* (c) https://github.com/MontiCore/monticore */
package automata2cd;

import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;

import java.util.Collection;

public class Automata2CDData {
  
  protected final ASTCDCompilationUnit compilationUnit;
  protected final ASTCDClass automataClass;
  protected final ASTCDClass stateSuperClass;
  protected final Collection<ASTCDClass> stateClasses;
  
  public Automata2CDData(ASTCDCompilationUnit compilationUnit, ASTCDClass automataClass,
                   ASTCDClass stateSuperClass, Collection<ASTCDClass> stateClasses) {
    this.compilationUnit = compilationUnit;
    this.automataClass = automataClass;
    this.stateSuperClass = stateSuperClass;
    this.stateClasses = stateClasses;
  }
  
  public ASTCDCompilationUnit getCompilationUnit() {
    return compilationUnit;
  }
  
  public ASTCDClass getAutomataClass() {
    return automataClass;
  }
  
  public ASTCDClass getStateSuperClass() {
    return stateSuperClass;
  }
  
  public Collection<ASTCDClass> getStateClasses() {
    return stateClasses;
  }
}
