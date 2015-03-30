/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package visitors;

import static org.junit.Assert.assertEquals;
import lang.AbstractTest;

import org.junit.Test;

import visitors.CountStates;
import _ast.ASTAutomaton;

/**
 * Test for {@link CountStates}.
 *
 * @author Robert Heim
 */
public class CountStatesTest extends AbstractTest {
  
  @Test
  public void test() {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/visitors/valid/A.aut");
    CountStates cs = new CountStates();
    cs.handle(automaton);
    assertEquals(3, cs.getCount());
  }
}
