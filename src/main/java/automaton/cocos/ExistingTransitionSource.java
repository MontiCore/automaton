/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import automaton._ast.ASTTransition;
import automaton._cocos.AutomatonASTTransitionCoCo;
import automaton.symboltable.StateSymbol;
import com.google.common.base.Optional;
import de.monticore.cocos.CoCoHelper;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;

import static com.google.common.base.Preconditions.checkArgument;

public class ExistingTransitionSource implements AutomatonASTTransitionCoCo {
  public static final String ERROR_CODE = "0xAUT03";
  
  public static final String ERROR_MSG_FORMAT =
      "The source state of the transition does not exist.";
  
  @Override
  public void check(ASTTransition node) {
    checkArgument(node.getEnclosingScope().isPresent());
    
    Scope enclosingScope = node.getEnclosingScope().get();
    Optional<StateSymbol> sourceState = enclosingScope.resolve(node.getFrom(), StateSymbol.KIND);
    
    if (!sourceState.isPresent()) {
      // Issue error...
      Log.error(CoCoHelper.buildErrorMsg(
          ERROR_CODE,
          ERROR_MSG_FORMAT,
          node.get_SourcePositionStart()));
    }
  }
}
