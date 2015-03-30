/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * TODO: Write me!
 *
 * @author (last commit) $Author$
 * @version $Revision$, $Date$
 */
public class AutomatonToolTest {
  
  @Test
  public void executeMain() {
    AutomatonTool.main(new String[] { "src/main/resources/example/PingPong.aut" });
    
    assertTrue(!false);
  }
  
}
