/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.cocos;

import automata._ast.ASTState;
import automata._cocos.AutomataASTStateCoCo;
import de.se_rwth.commons.logging.Log;

public class StateNameStartsWithCapitalLetter implements AutomataASTStateCoCo {
  
  @Override
  public void check(ASTState state) {
    String stateName = state.getName();
    boolean startsWithUpperCase = Character.isUpperCase(stateName.charAt(0));
    
    if (!startsWithUpperCase) {
      // Issue warning...
      Log.warn(
          String.format("0xAUT02 State name '%s' should start with a capital letter.", stateName),
          state.get_SourcePositionStart());
    }
  }
  
}
