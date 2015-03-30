/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package prettyprint;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import lang.AbstractTest;

import org.junit.Test;

import prettyprint.PrettyPrinter;
import _ast.ASTAutomaton;

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
    String expected = "automaton A {\n  state S\n  //custom ASTState node\n  <<initial>> <<final>>;\n}\n";
    assertEquals(expected, actual);
  }
  
}
