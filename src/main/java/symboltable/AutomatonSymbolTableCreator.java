/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import static java.util.Objects.requireNonNull;
import _ast.ASTState;
import _ast.ASTAutomaton;
import _ast.ASTAutomatonBase;
import _ast.ASTTransition;
import _visitor.AutomatonVisitor;
import de.monticore.symboltable.Scope;
import de.monticore.symboltable.SymbolTableCreator;

public interface AutomatonSymbolTableCreator extends AutomatonVisitor, SymbolTableCreator {
  
  /**
   * Creates the symbol table starting from the <code>rootNode</code> and
   * returns the first scope that was created.
   *
   * @param rootNode the root node
   * @return the first scope that was created
   */
  public default Scope createFromAST(ASTAutomatonBase rootNode) {
    requireNonNull(rootNode);
    rootNode.accept(this);
    return getFirstCreatedScope();
  }
  
  @Override
  public default void visit(final ASTAutomaton automatonNode) {
    final AutomatonSymbol automaton = new AutomatonSymbol(automatonNode.getName());
    
    defineInScopeAndSetLinkBetweenSymbolAndAst(automaton, automatonNode);
    putScopeOnStackAndSetEnclosingIfExists(automaton);
  }
  
  @Override
  public default void endVisit(final ASTAutomaton automatonNode) {
    removeCurrentScope();
  }
  
  @Override
  public default void visit(final ASTState stateNode) {
    final StateSymbol stateSymbol = new StateSymbol(stateNode.getName());
    
    stateSymbol.setInitial(stateNode.isInitial());
    stateSymbol.setFinal(stateNode.isFinal());
    
    defineInScopeAndSetLinkBetweenSymbolAndAst(stateSymbol, stateNode);
  }
  
  @Override
  public default void visit(final ASTTransition transitionNode) {
    final StateSymbolReference fromState =
        new StateSymbolReference(transitionNode.getFrom(), currentScope().get());
    final StateSymbolReference toState =
        new StateSymbolReference(transitionNode.getTo(), currentScope().get());
    
    // TODO PN What is the name of a transition?
    final TransitionSymbol transitionSymbol =
        new TransitionSymbol(transitionNode.getInput(), fromState, toState);
    
    defineInScopeAndSetLinkBetweenSymbolAndAst(transitionSymbol, transitionNode);
  }
}
