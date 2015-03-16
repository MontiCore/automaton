/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.cocos;

import de.monticore.automaton._ast.ASTState;
import de.monticore.automaton._cocos.AutomatonASTStateCoCo;
import de.monticore.cocos.CoCoHelper;
import de.se_rwth.commons.logging.Log;

public class StateNameStartsWithCapitalLetter implements AutomatonASTStateCoCo {
  public static final String ERROR_CODE = "0xAUT02";
  public static final String ERROR_MSG_FORMAT =
      "State name '%s' should start with a capital letter.";

  @Override
  public void check(ASTState state) {
    String stateName = state.getName();
    boolean startsWithUpperCase = Character.isUpperCase(stateName.charAt(0));

    if (!startsWithUpperCase) {
      // Warning output
      Log.warn(CoCoHelper.buildErrorMsg(
          ERROR_CODE,
          String.format(ERROR_MSG_FORMAT, stateName),
          state.get_SourcePositionStart()));
    }
  }

}
