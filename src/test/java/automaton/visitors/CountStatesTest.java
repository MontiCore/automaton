/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.visitors;

import static org.junit.Assert.assertEquals;
import automaton.lang.AbstractTest;

import org.junit.Test;

import automaton._ast.ASTAutomaton;
import automaton.visitors.CountStates;

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
