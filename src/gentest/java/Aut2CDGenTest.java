/* (c) https://github.com/MontiCore/monticore */

import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._parser.AutomataParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import output.PingPong;
import output.Stimuli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Aut2CDGenTest {
  
  @Test
  public void testGenerator() throws IOException {
    final Optional<ASTAutomaton> optAST = new AutomataParser().parse("src/gentest/resources/PingPong.aut");
    assertTrue(optAST.isPresent());
    final ASTAutomaton ast = optAST.get();
  
    AutomataTool tool = new AutomataTool();
    tool.generateCD(ast, "src/gentest/java/output/");
  }

  @Test
  public void testValidInputs() {
    PingPong p = new PingPong();
    List<Stimuli> list  = new ArrayList<>();
    list.add(Stimuli.startGame);
    list.add(Stimuli.returnBall);
    list.add(Stimuli.stopGame);
    list.add(Stimuli.startGame);
    list.add(Stimuli.returnBall);
    assertTrue(p.run(list));
  }

  @Test
  public void testInvalidInputs() {
    PingPong p = new PingPong();
    List<Stimuli> list  = new ArrayList<>();
    list.add(Stimuli.startGame);
    list.add(Stimuli.returnBall);
    list.add(Stimuli.stopGame);
    assertFalse(p.run(list));
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
