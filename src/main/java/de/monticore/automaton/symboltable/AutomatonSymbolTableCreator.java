/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import de.cd4analysis._ast.ASTAutomatonBase;
import de.cd4analysis._visitor.AutomatonVisitor;
import de.monticore.symboltable.Scope;
import de.monticore.symboltable.SymbolTableCreator;

import static java.util.Objects.requireNonNull;

public interface AutomatonSymbolTableCreator extends AutomatonVisitor, SymbolTableCreator {

  /**
   * Creates the symbol table starting from the <code>rootNode</code> and returns the first scope
   * that was created.
   *
   * @param rootNode the root node
   * @return the first scope that was created
   */
  public default Scope createFromAST(ASTAutomatonBase rootNode) {
    requireNonNull(rootNode);
    rootNode.accept(this);
    return getFirstCreatedScope();
  }

}
