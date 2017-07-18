/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.lang;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.antlr.v4.runtime.RecognitionException;

import automaton._ast.ASTAutomaton;
import automaton._parser.AutomatonParser;

/**
 * Provides some helpers for tests.
 *
 * @author Robert Heim
 */
abstract public class AbstractTest {
  
  /**
   * Parses a model and ensures that the root node is present.
   * 
   * @param modelFile the full file name of the model.
   * @return the root of the parsed model.
   */
  protected ASTAutomaton parseModel(String modelFile) {
    Path model = Paths.get(modelFile);
    AutomatonParser parser = new AutomatonParser();
    Optional<ASTAutomaton> optAutomaton;
    try {
      optAutomaton = parser.parse(model.toString());
      assertFalse(parser.hasErrors());
      assertTrue(optAutomaton.isPresent());
      return optAutomaton.get();
    }
    catch (RecognitionException | IOException e) {
      e.printStackTrace();
      fail("There was an exception when parsing the model " + modelFile + ": "
          + e.getMessage());
    }
    return null;
  }
}
