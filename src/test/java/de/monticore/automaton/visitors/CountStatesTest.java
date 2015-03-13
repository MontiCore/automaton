/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.visitors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.monticore.automaton.AbstractTest;
import de.monticore.automaton._ast.ASTAutomaton;

/**
 * Test for {@link CountStates}.
 *
 * @author Robert Heim
 */
public class CountStatesTest extends AbstractTest {
  
  @Test
  public void test() {
    ASTAutomaton automaton = parseModel("src/test/resources/de/monticore/automaton/visitors/valid/A.aut");
    CountStates cs = new CountStates();
    cs.handle(automaton);
    assertEquals(3, cs.getCount());
  }
}
