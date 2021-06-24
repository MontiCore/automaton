/* (c) https://github.com/MontiCore/monticore */
package automata;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * A very short test for the main function
 *
 */
public class AutomataCLITest {
  
  @Test
  public void executeMain() {
    AutomataCLI.main(new String[] { "src/main/resources/example/PingPong.aut" });
    
    assertTrue(!false);
  }
  
}
