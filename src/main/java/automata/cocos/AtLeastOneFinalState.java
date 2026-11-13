/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AtLeastOneFinalState implements AutomataASTAutomatonCoCo {

  @Override
  public void check(ASTAutomaton automaton) {
    boolean finalState = false;

    for (ASTState state : automaton.getStateList()) {
      if (state.isFinal()) {
        finalState = true;
        break;
      }
    }

    if (!finalState) {
      Log.error(
          String.format("0xB4115 Automaton %s must have at least one final state.", automaton.getName()),
          automaton.get_SourcePositionStart(),
          automaton.get_SourcePositionEnd()
      );
    }
  }

}
