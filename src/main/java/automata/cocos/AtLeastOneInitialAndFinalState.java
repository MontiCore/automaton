/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata._ast.ASTAutomaton;
import automata._ast.ASTState;
import automata._cocos.AutomataASTAutomatonCoCo;
import de.se_rwth.commons.logging.Log;

public class AtLeastOneInitialAndFinalState implements AutomataASTAutomatonCoCo {

  @Override
  public void check(ASTAutomaton automaton) {
    boolean hasInitial = false;
    boolean hasFinal = false;

    for (ASTState state : automaton.getStateList()) {
      if (state.isInitial()) {
        hasInitial = true;
      }
      if (state.isFinal()) {
        hasFinal = true;
      }
    }

    if (!hasInitial) {
      // Issue error...
      Log.error("0xB4111 An automaton must have one initial state. Do this by adding <<initial>> after a state name.", automaton.get_SourcePositionStart());
    }

    if (!hasFinal) {
      // Issue error...
      Log.error("0xB4112 An automaton must have at least one final state. Do this by adding <<final>> after a state name.", automaton.get_SourcePositionStart());
    }
  }

}
