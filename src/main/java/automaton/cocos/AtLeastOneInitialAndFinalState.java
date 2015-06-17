/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import automaton._ast.ASTAutomaton;
import automaton._ast.ASTState;
import automaton._cocos.AutomatonASTAutomatonCoCo;
import de.monticore.cocos.CoCoLog;

public class AtLeastOneInitialAndFinalState implements AutomatonASTAutomatonCoCo {
  public static final String ERROR_CODE = "0xA0114";
  
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
      CoCoLog.error(ERROR_CODE, ERROR_MSG_FORMAT, automaton.get_SourcePositionStart());
    }
  }
  
}
