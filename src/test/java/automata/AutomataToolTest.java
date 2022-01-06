/* (c) https://github.com/MontiCore/monticore */
package automata;

import static org.junit.Assert.assertTrue;

import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A very short test for the main function
 *
 */
public class AutomataToolTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }
  
  @Test
  public void executeMain() {
    AutomataTool.main(new String[] { "src/main/resources/example/PingPong.aut" });
    
    assertTrue(!false);
  }
  
}
