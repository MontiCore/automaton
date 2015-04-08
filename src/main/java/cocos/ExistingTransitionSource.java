/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import symboltable.StateSymbol;
import _ast.ASTTransition;
import _cocos.AutomatonASTTransitionCoCo;
import de.monticore.cocos.CoCoLog;
import de.monticore.symboltable.Scope;

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
      CoCoLog.error(ERROR_CODE, ERROR_MSG_FORMAT, node.get_SourcePositionStart());
    }
  }
}
