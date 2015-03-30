/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import _ast.ASTState;
import _ast.ASTAutomaton;
import _cocos.AutomatonASTAutomatonCoCo;
import de.monticore.cocos.CoCoHelper;
import de.se_rwth.commons.logging.Log;

public class AtLeastOneInitialAndFinalState implements AutomatonASTAutomatonCoCo {
  public static final String ERROR_CODE = "0xAUT01";
  
  public static final String ERROR_MSG_FORMAT =
      "An automaton must have at least one initial and one final state.";
  
  @Override
  public void check(ASTAutomaton automaton) {
    boolean initialState = false;
    boolean finalState = false;
    
    for (ASTState state : automaton.getStates()) {
      if (state.isInitial()) {
        initialState = true;
      }
      if (state.isFinal()) {
        finalState = true;
      }
    }
    
    if (!initialState || !finalState) {
      // Issue error...
      Log.error(CoCoHelper.buildErrorMsg(
          ERROR_CODE,
          ERROR_MSG_FORMAT,
          automaton.get_SourcePositionStart()));
    }
  }
  
}
