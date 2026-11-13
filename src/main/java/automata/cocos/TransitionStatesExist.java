/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata._ast.ASTTransition;
import automata._cocos.AutomataASTTransitionCoCo;
import automata._symboltable.IAutomataScope;
import automata._symboltable.StateSymbol;
import de.se_rwth.commons.logging.Log;

import java.util.Optional;

public class TransitionStatesExist implements AutomataASTTransitionCoCo {

  @Override
  public void check(ASTTransition node) {
    IAutomataScope enclosingScope = node.getEnclosingScope();
    Optional<StateSymbol> sourceState = enclosingScope.resolveState(node.getFrom());

    if (!sourceState.isPresent()) {
      // Issue error...
      Log.error(String.format(
          "0xB4003 The source state '%s' of the transition does not exist.", node.getFrom()
        ),
        node.get_SourcePositionStart(),
        node.get_SourcePositionEnd()
      );
    }

    Optional<StateSymbol> targetState = enclosingScope.resolveState(node.getTo());

    if (!targetState.isPresent()) {
      // Issue error...
      Log.error(String.format(
            "0xB4004 The target state '%s' of the transition does not exist.", node.getTo()
          ),
          node.get_SourcePositionStart(),
          node.get_SourcePositionEnd()
      );
    }
  }
}
