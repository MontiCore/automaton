/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.cocos;

import de.monticore.automaton._cocos.AutomatonCoCoChecker;

public class AutomatonCoCos {
  
  public AutomatonCoCoChecker getCheckerForAllCoCos() {
    final AutomatonCoCoChecker checker = new AutomatonCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    // TODO PN uncomment
    // checker.addCoCo(new ExistingTransitionSource());
    
    return checker;
  }
}
