/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.visitors;

import automata._ast.ASTState;
import automata._visitor.AutomataVisitor;

/**
 * Counts the states of an automata.
 *
 * @author Robert Heim
 */
public class CountStates implements AutomataVisitor {
  private int count = 0;
  
  @Override
  public void visit(ASTState node) {
    count++;
  }
  
  public int getCount() {
    return count;
  }
}
