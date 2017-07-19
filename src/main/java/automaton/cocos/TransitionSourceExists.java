/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import automaton._ast.ASTTransition;
import automaton._cocos.AutomatonASTTransitionCoCo;
import automaton._symboltable.StateSymbol;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;

public class TransitionSourceExists implements AutomatonASTTransitionCoCo {
  
  @Override
  public void check(ASTTransition node) {
    checkArgument(node.getEnclosingScope().isPresent());
    
    Scope enclosingScope = node.getEnclosingScope().get();
    Optional<StateSymbol> sourceState = enclosingScope.resolve(node.getFrom(), StateSymbol.KIND);
    
    if (!sourceState.isPresent()) {
      // Issue error...
      Log.error("0xAUT03 The source state of the transition does not exist.",
          node.get_SourcePositionStart());
    }
  }
}
