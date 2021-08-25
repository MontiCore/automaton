/* (c) https://github.com/MontiCore/monticore */
package automata.parser;

import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._parser.AutomataParser;
import automata.lang.AbstractTest;
import de.se_rwth.commons.logging.Log;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.*;

public class AutomatonParserTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }
  
  @Test
  public void testPingPong() {
    ASTAutomaton a = parseModel("src/test/resources/automata/parser/PingPong.aut");
    assertNotNull(a);
  }
  
  @Test
  public void testState() throws RecognitionException, IOException {
    AutomataParser parser = new AutomataParser();
    Optional<ASTState> state = parser.parseState(
            new StringReader("state Ping;"));
    assertFalse(parser.hasErrors());
    assertTrue(state.isPresent());
  }
}
