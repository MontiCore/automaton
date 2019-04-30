/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata._symboltable;

import java.util.Collection;
import java.util.Optional;
import static de.monticore.symboltable.Symbols.sortSymbolsByPosition;

public class AutomatonSymbol extends AutomatonSymbolTOP {

  public AutomatonSymbol(final String name) {
    super(name);
  }

  @Override
  protected AutomataScope createSpannedScope() {
    return new AutomataScope();
  }

  public Optional<StateSymbol> getState(final String name) {
    return getSpannedScope().resolveLocally(name, StateSymbol.KIND);
  }

  public Collection<StateSymbol> getStates() {
    return sortSymbolsByPosition(getSpannedScope().resolveLocally(StateSymbol.KIND));
  }
}
