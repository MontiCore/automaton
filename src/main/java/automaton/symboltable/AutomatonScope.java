/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.symboltable;

import de.monticore.symboltable.CommonScope;
import de.monticore.symboltable.MutableScope;

import java.util.Optional;

public class AutomatonScope extends CommonScope {

  public AutomatonScope(Optional<MutableScope> enclosingScope) {
    super(enclosingScope, true);
  }
}
