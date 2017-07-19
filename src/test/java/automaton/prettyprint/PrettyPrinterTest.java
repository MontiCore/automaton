/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.prettyprint;

import automaton._ast.ASTAutomaton;
import automaton.prettyprint.PrettyPrinter;
import automaton.lang.AbstractTest;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link PrettyPrinter}.
 *
 * @author Robert Heim
 */
public class PrettyPrinterTest extends AbstractTest {
  @Test
  public void test() throws IOException
  {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/prettyprinter/valid/A.aut");
    PrettyPrinter pp = new PrettyPrinter();
    pp.handle(automaton);
    
    String actual = pp.getResult();
    String expected = "automaton A {\n  state S <<initial>> <<final>>;\n}\n";
    assertEquals(expected, actual);
  }
  
}
