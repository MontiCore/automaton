/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import _ast.ASTAutomaton;
import _ast.ASTAutomatonBase;
import _ast.ASTState;
import _ast.ASTTransition;
import _visitor.AutomatonVisitor;
import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.Scope;
import de.monticore.symboltable.SymbolTableCreator;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public interface AutomatonSymbolTableCreator extends AutomatonVisitor, SymbolTableCreator {
  
  /**
   * Creates the symbol table starting from the <code>rootNode</code> and
   * returns the first scope that was created.
   *
   * @param rootNode the root node
   * @return the first scope that was created
   */
  default Scope createFromAST(ASTAutomatonBase rootNode) {
    requireNonNull(rootNode);
    rootNode.accept(this);
    return getFirstCreatedScope();
  }
  
  @Override
  default void visit(final ASTAutomaton automatonNode) {
    final ArtifactScope artifactScope = new ArtifactScope(Optional.empty(), "", new ArrayList<>());
    putOnStackAndSetEnclosingIfExists(artifactScope);

    final AutomatonSymbol automaton = new AutomatonSymbol(automatonNode.getName());
    defineInScopeAndLinkWithAst(automaton, automatonNode);
  }
  
  @Override
  default void endVisit(final ASTAutomaton automatonNode) {
    removeCurrentScope();
  }
  
  @Override
  default void visit(final ASTState stateNode) {
    final StateSymbol stateSymbol = new StateSymbol(stateNode.getName());
    
    stateSymbol.setInitial(stateNode.isInitial());
    stateSymbol.setFinal(stateNode.isFinal());

    defineInScopeAndLinkWithAst(stateSymbol, stateNode);
  }
  
  @Override
  default void visit(final ASTTransition transitionNode) {
    final StateSymbolReference fromState =
        new StateSymbolReference(transitionNode.getFrom(), currentScope().get());
    final StateSymbolReference toState =
        new StateSymbolReference(transitionNode.getTo(), currentScope().get());
    
    // TODO PN What is the name of a transition?
    final TransitionSymbol transitionSymbol =
        new TransitionSymbol(transitionNode.getInput(), fromState, toState);

    defineInScopeAndLinkWithAst(transitionSymbol, transitionNode);
  }
}
