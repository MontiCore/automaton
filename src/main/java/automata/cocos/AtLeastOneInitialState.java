/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AtLeastOneInitialState implements AutomataASTAutomatonCoCo {

  @Override
  public void check(ASTAutomaton automaton) {
    boolean initialState = false;

    for (ASTState state : automaton.getStateList()) {
      if (state.isInitial()) {
        initialState = true;
        break;
      }
    }

    if (!initialState) {
      Log.error(String.format("0xB4114 Automaton %s must have at least one initial state.",
              automaton.getName()
          ),
          automaton.get_SourcePositionStart(),
          automaton.get_SourcePositionEnd());
    }
  }

}
