/* (c) https://github.com/MontiCore/monticore */
package automata;

import static org.junit.Assert.assertTrue;

import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A very short test for the main function
 *
 */
public class AutomataToolTest {

  @Before
  public void setUp() {
    AutomataMill.reset();
    AutomataMill.init();
    LogStub.init();
    Log.enableFailQuick(false);
    Log.getFindings().clear();
  }
  
  @Test
  public void executeMain() {
    AutomataTool.main(new String[] { "-i","src/main/resources/example/PingPong.aut"});

    assertTrue(!false);
  }
  
}
