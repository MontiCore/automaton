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
import de.monticore.symboltable.CommonSymbolTableCreator;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class AutomatonSymbolTableCreator extends CommonSymbolTableCreator implements AutomatonVisitor {

  public AutomatonSymbolTableCreator(final ResolverConfiguration resolverConfig, final MutableScope enclosingScope) {
    super(resolverConfig, enclosingScope);
  }

  /**
   * Creates the symbol table starting from the <code>rootNode</code> and
   * returns the first scope that was created.
   *
   * @param rootNode the root node
   * @return the first scope that was created
   */
  public Scope createFromAST(ASTAutomatonBase rootNode) {
    requireNonNull(rootNode);
    rootNode.accept(this);
    return getFirstCreatedScope();
  }
  
  @Override
  public void visit(final ASTAutomaton automatonNode) {
    final ArtifactScope artifactScope = new ArtifactScope(Optional.empty(), "", new ArrayList<>());
    putOnStackAndSetEnclosingIfExists(artifactScope);

    final AutomatonSymbol automaton = new AutomatonSymbol(automatonNode.getName());
    defineInScopeAndLinkWithAst(automaton, automatonNode);
  }
  
  @Override
  public void endVisit(final ASTAutomaton automatonNode) {
    removeCurrentScope();
  }
  
  @Override
  public void visit(final ASTState stateNode) {
    final StateSymbol stateSymbol = new StateSymbol(stateNode.getName());
    
    stateSymbol.setInitial(stateNode.isInitial());
    stateSymbol.setFinal(stateNode.isFinal());

    defineInScopeAndLinkWithAst(stateSymbol, stateNode);
  }
  
  @Override
  public void visit(final ASTTransition transitionNode) {
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
