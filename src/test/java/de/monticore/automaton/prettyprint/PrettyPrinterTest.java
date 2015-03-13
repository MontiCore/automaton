/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.prettyprint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.common.base.Optional;

import de.monticore.automaton._ast.ASTAutomaton;
import de.monticore.automaton._parser.AutomatonMCParser;

/**
 * Test for {@link PrettyPrinter}.
 *
 * @author Robert Heim
 */
public class PrettyPrinterTest {
  @Test
  public void test() throws IOException
  {
    PrettyPrinter pp = new PrettyPrinter();
    
    Path model = Paths.get("src/test/resources/de/monticore/automaton/prettyprinter/valid/A.aut");
    AutomatonMCParser parser = new AutomatonMCParser();
    Optional<ASTAutomaton> optAutomaton = parser.parse(model.toString());
    assertTrue(optAutomaton.isPresent());
    ASTAutomaton automaton = optAutomaton.get();
    
    pp.handle(automaton);
    
    String actual = pp.getResult();
    String expected = "automaton A {\n  state S <<initial>> <<final>>;\n}\n";
    assertEquals(expected, actual);
  }
  
}
