/* (c) https://github.com/MontiCore/monticore */
package automata.prettyprint;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._ast.ASTTransition;
import automata._prettyprint.AutomataPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;

public class MyAutPrettyPrinter extends AutomataPrettyPrinter {
  
  public MyAutPrettyPrinter(IndentPrinter printer, boolean printComments) {
    super(printer, printComments);
  }
  
  @Override
  public FormattingPrinter getPrinter() {
    return (FormattingPrinter) super.getPrinter();
  }
  
  @Override
  public void handle(ASTAutomaton node) {
    // TODO: Generate this class
    getPrinter().startProduction("Automaton");
    getPrinter().emit("automaton", "AUTOMATON1673671408", "0"); // pos = tmp0
    getPrinter().emit(node.getName(), "Name", "name"); // pos = usage-name = "name"
    getPrinter().emit("{", "LCURLY", "2");
    
    for (ASTState state : node.getStateList()) {
      state.accept(getTraverser());
    }
    for (ASTTransition transition : node.getTransitionList()) {
      transition.accept(getTraverser());
    }
    
    getPrinter().emit("}", "RCURLY", "5");
    getPrinter().endProduction();
  }
  
  @Override
  public void handle(ASTState node) {
    getPrinter().startProduction("State");
    getPrinter().emit("state", "STATE109757585", "0");
    getPrinter().emit(node.getName(), "Name", "name");
    if (node.isInitial()) {
      getPrinter().emit("<<", "LTLT", "2");
      getPrinter().emit("initial", "INITIAL1948342084", "initial");
      getPrinter().emit(">>", "GTGT", "4");
    }
    if (node.isFinal()) {
      getPrinter().emit("<<", "LTLT", "5");
      getPrinter().emit("final", "FINAL97436022", "final");
      getPrinter().emit(">>", "GTGT", "7");
    }
    getPrinter().emit("{", "LCURLY", "8");
    
    for (ASTState state : node.getStateList()) {
      state.accept(getTraverser());
    }
    for (ASTTransition transition : node.getTransitionList()) {
      transition.accept(getTraverser());
    }
    
    getPrinter().emit("}", "RCURLY", "11");
    getPrinter().endProduction();
  }
  
}
