/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;

import lang.AbstractTest;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import _ast.ASTState;
import _ast.ASTAutomaton;
import _parser.AutomatonParserFactory;
import _parser.StateMCParser;

import com.google.common.base.Optional;

public class AutomatonParserTest extends AbstractTest {
  
  @Test
  public void testPingPong() {
    ASTAutomaton a = parseModel("src/test/resources/automaton/parser/PingPong.aut");
    assertNotNull(a);
  }
  
  @Test
  public void testState() throws RecognitionException, IOException {
    StateMCParser parser = AutomatonParserFactory.createStateMCParser();
    Optional<ASTState> state = parser.parse(
        new StringReader("state Ping;"));
    assertFalse(parser.hasErrors());
    assertTrue(state.isPresent());
  }
  
}
