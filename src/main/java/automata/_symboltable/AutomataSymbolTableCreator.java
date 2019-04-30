/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata._symboltable;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Optional;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._ast.ASTTransition;
import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;

public class AutomataSymbolTableCreator extends AutomataSymbolTableCreatorTOP {
  
  public AutomataSymbolTableCreator(
      final ResolvingConfiguration resolverConfig,
      final Scope enclosingScope) {
    super(resolverConfig, enclosingScope);
  }

  public ArtifactScope createFromAST(ASTAutomaton rootNode) {
      requireNonNull(rootNode);

      final ArtifactScope artifactScope = new ArtifactScope(Optional.empty(), "", new ArrayList<>());
      putOnStack(artifactScope);

      rootNode.accept(this);

      return artifactScope;
  }

  @Override
  public void visit(final ASTAutomaton automatonNode) {
    final AutomatonSymbol automaton = new AutomatonSymbol(automatonNode.getName());
    addToScopeAndLinkWithNode(automaton, automatonNode);
  }
  
  @Override
  public void endVisit(final ASTAutomaton automatonNode) {
    removeCurrentScope();
  }
  
  @Override
  public void visit(final ASTState stateNode) {
    final StateSymbol stateSymbol = new StateSymbol(stateNode.getName());

    addToScopeAndLinkWithNode(stateSymbol, stateNode);
  }

  @Override
  public void endVisit(final ASTState node) {
    removeCurrentScope();
  }

  @Override
  public void visit(final ASTTransition transitionNode) {
    transitionNode.setEnclosingScope(currentScope().get());
  }
}
