/* (c) https://github.com/MontiCore/monticore */

import org.junit.Test;
import pingpong.PingPong;
import pingpong.Stimuli;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GeneratedClassesTest {
  
  @Test
  public void testValidAutomaton() {
    PingPong pingPong = new PingPong();
    List<Stimuli> stimuli =
      Arrays.asList(Stimuli.startGame, Stimuli.returnBall, Stimuli.stopGame, Stimuli.startGame, Stimuli.returnBall);
    assertTrue(pingPong.run(stimuli));
  }
  
  @Test
  public void testInvalidAutomaton() {
    PingPong pingPong = new PingPong();
    List<Stimuli> stimuli =
      Arrays.asList(Stimuli.startGame, Stimuli.returnBall, Stimuli.returnBall, Stimuli.stopGame);
    assertFalse(pingPong.run(stimuli));
  }
}
