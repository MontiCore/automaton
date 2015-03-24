/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton;

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
    AutomatonTool
        .main(new String[] { "src/test/resources/de/monticore/automaton/parser/PingPong.aut" });
    
    assertTrue(!false);
  }
  
}
