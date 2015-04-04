/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import _ast.ASTState;
import _cocos.AutomatonASTStateCoCo;
import de.monticore.cocos.CoCoLog;

public class StateNameStartsWithCapitalLetter implements AutomatonASTStateCoCo {
  public static final String ERROR_CODE = "0xAUT02";
  
  public static final String ERROR_MSG_FORMAT =
      "State name '%s' should start with a capital letter.";
  
  @Override
  public void check(ASTState state) {
    String stateName = state.getName();
    boolean startsWithUpperCase = Character.isUpperCase(stateName.charAt(0));
    
    if (!startsWithUpperCase) {
      // Issue warning...
      CoCoLog.warn(ERROR_CODE, String.format(ERROR_MSG_FORMAT, stateName),
          state.get_SourcePositionStart());
    }
  }
  
}
