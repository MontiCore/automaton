/* (c) https://github.com/MontiCore/monticore */

import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._parser.AutomataParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class Aut2CDGenTest {
  
  @Test
  public void testGenerator() throws IOException {
    final Optional<ASTAutomaton> optAST = new AutomataParser().parse("src/gentest/resources/PingPong.aut");
    assertTrue(optAST.isPresent());
    final ASTAutomaton ast = optAST.get();
  
    AutomataTool tool = new AutomataTool();
    tool.generateCD(ast, "src/gentest/resources/output/");
  
  }
  
  @Before
  public void before() {
    LogStub.init();
    Log.enableFailQuick(false);
  }
  
  @After
  public void after() {
    assertTrue(Log.getFindings().isEmpty());
  }
  
}
