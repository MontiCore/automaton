/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.montciore.automaton.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import com.google.common.base.Optional;

import de.monticore.automaton._ast.ASTAutomaton;
import de.monticore.automaton._ast.ASTState;
import de.monticore.automaton._parser.AutomatonMCParser;
import de.monticore.automaton._parser.StateMCParser;

public class AutomatonParserTest {
  
  @Test
  public void testPingPong() throws RecognitionException, IOException {
    Path path = Paths.get(
        "src/test/resources/de/monticore/automaton/parser/PingPong.aut");
    AutomatonMCParser parser = new AutomatonMCParser();
    Optional<ASTAutomaton> aut = parser.parse(
        path.toString());
    assertFalse(parser.hasErrors());
    assertTrue(aut.isPresent());
  }
  
  @Test
  public void testState() throws RecognitionException, IOException {
    StateMCParser parser = new StateMCParser();
    Optional<ASTState> state = parser.parse(
        new StringReader("state Ping;"));
    assertFalse(parser.hasErrors());
    assertTrue(state.isPresent());
  }

}
