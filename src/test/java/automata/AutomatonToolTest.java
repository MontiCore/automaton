package automata;
/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * TODO: Write me!
 *
 * @author (last commit) $Author$
 */
public class AutomatonToolTest {
  
  @Test
  public void executeMain() {
    AutomataTool.main(new String[] { "src/main/resources/example/PingPong.aut" });
    
    assertTrue(!false);
  }
  
}
