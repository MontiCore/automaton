/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import java.util.List;

import com.google.common.base.Optional;

import de.monticore.symboltable.CommonScopeSpanningSymbol;
import de.monticore.symboltable.SymbolKind;

public class AutomatonSymbol extends CommonScopeSpanningSymbol {
  
  public static final AutomatonKind KIND = new AutomatonKind();
  
  public AutomatonSymbol(final String name) {
    super(name, KIND);
  }
  
  public Optional<StateSymbol> getState(final String name) {
    return getSpannedScope().resolveLocally(name, StateSymbol.KIND);
  }
  
  public List<StateSymbol> getStates() {
    return getSpannedScope().resolveLocally(StateSymbol.KIND);
  }
  
  public List<TransitionSymbol> getTransitions() {
    return getSpannedScope().resolveLocally(TransitionSymbol.KIND);
  }
  
  static final class AutomatonKind extends SymbolKind {
    AutomatonKind() {
    }
  }
}
