/* (c) https://github.com/MontiCore/monticore */
package automata.visitors;

import automata.AutomataMill;
import automata._ast.ASTState;
import automata._visitor.AutomataHandler;
import automata._visitor.AutomataTraverser;
import automata._visitor.AutomataVisitor;

/**
 * Counts the states of an automata.
 */
public class CountStates implements AutomataHandler {
  private int count = 0;
  private AutomataTraverser traverser;

  public CountStates() {
    this.traverser = AutomataMill.traverser();
    traverser.setAutomataHandler(this);
  }

  @Override
  public void handle(ASTState node) {
    count++;
    getTraverser().traverse(node);
  }

  public int getCount() {
    return count;
  }

  @Override
  public AutomataTraverser getTraverser() {
    return this.traverser;
  }

  @Override
  public void setTraverser(AutomataTraverser traverser) {
    this.traverser = traverser;
  }
}
