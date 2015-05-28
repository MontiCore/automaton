/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import de.monticore.symboltable.CommonScopeSpanningSymbol;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.SymbolKind;

import java.util.List;
import java.util.Optional;

public class AutomatonSymbol extends CommonScopeSpanningSymbol {
  
  public static final AutomatonKind KIND = new AutomatonKind();
  
  public AutomatonSymbol(final String name) {
    super(name, KIND);
  }

  @Override
  protected MutableScope createSpannedScope() {
    return new AutomatonScope(Optional.empty());
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