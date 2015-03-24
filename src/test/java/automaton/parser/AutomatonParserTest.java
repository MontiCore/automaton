/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package automaton.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import automaton.AbstractTest;
import automaton._ast.ASTAutomaton;
import automaton._ast.ASTState;
import automaton._parser.StateMCParser;

import com.google.common.base.Optional;

public class AutomatonParserTest extends AbstractTest {
  
  @Test
  public void testPingPong() {
    ASTAutomaton a = parseModel("src/test/resources/automaton/parser/PingPong.aut");
    assertNotNull(a);
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
