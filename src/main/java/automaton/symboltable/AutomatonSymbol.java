/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.symboltable;

import java.util.List;
import java.util.Optional;

import de.monticore.symboltable.CommonScopeSpanningSymbol;
import de.monticore.symboltable.SymbolKind;

public class AutomatonSymbol extends CommonScopeSpanningSymbol {
  
  public static final AutomatonKind KIND = new AutomatonKind();
  
  public AutomatonSymbol(final String name) {
    super(name, KIND);
  }

  @Override
  protected AutomatonScope createSpannedScope() {
    return new AutomatonScope(Optional.empty());
  }

  public Optional<StateSymbol> getState(final String name) {
    return getSpannedScope().resolveLocally(name, StateSymbol.KIND);
  }
  
  public List<StateSymbol> getStates() {
    return getSpannedScope().resolveLocally(StateSymbol.KIND);
  }
  
  static final class AutomatonKind extends SymbolKind {
    AutomatonKind() {
    }
  }
}
