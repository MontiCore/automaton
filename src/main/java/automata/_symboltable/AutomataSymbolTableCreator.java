/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata._symboltable;

import automata._ast.ASTTransition;
import automata._visitor.AutomataVisitor;

import java.util.Deque;

public class AutomataSymbolTableCreator extends AutomataSymbolTableCreatorTOP
    implements AutomataVisitor {

  protected AutomataVisitor realThis = this;

  @Override public void setRealThis(AutomataVisitor realThis) {
    this.realThis = realThis;
  }

  @Override public AutomataVisitor getRealThis() {
    return this.realThis;
  }
  
  public AutomataSymbolTableCreator(
            final IAutomataScope enclosingScope) {
    super(enclosingScope);
  }
  
  public AutomataSymbolTableCreator(
      Deque<? extends IAutomataScope> scopeStack) {
    super(scopeStack);
  }

  @Override  // TODO AB generate method
  public void visit(final ASTTransition transitionNode) {
    transitionNode.setEnclosingScope(getCurrentScope().get());

  }
}
