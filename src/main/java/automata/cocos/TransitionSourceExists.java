/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.cocos;

import automata._ast.ASTTransition;
import automata._cocos.AutomataASTTransitionCoCo;
import automata._symboltable.IAutomataScope;
import automata._symboltable.StateSymbol;
import de.se_rwth.commons.logging.Log;

import java.util.Optional;

public class TransitionSourceExists implements AutomataASTTransitionCoCo {
  
  @Override
  public void check(ASTTransition node) {
    IAutomataScope enclosingScope = node.getEnclosingScope2();
    Optional<StateSymbol> sourceState = enclosingScope.resolveState(node.getFrom());
    
    if (!sourceState.isPresent()) {
      // Issue error...
      Log.error("0xAUT03 The source state of the transition does not exist.",
          node.get_SourcePositionStart());
    }
  }
}
