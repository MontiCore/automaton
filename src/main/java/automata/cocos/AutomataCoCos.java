/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.cocos;

import automata._cocos.AutomataCoCoChecker;

public class AutomataCoCos {
  
  public AutomataCoCoChecker getCheckerForAllCoCos() {
    final AutomataCoCoChecker checker = new AutomataCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    // TODO PN uncomment
    // checker.addCoCo(new TransitionSourceExists());
    
    return checker;
  }
}
