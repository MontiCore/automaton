/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.symboltable;

import de.monticore.symboltable.Scope;
import de.monticore.symboltable.references.CommonSymbolReference;
import de.monticore.symboltable.references.SymbolReference;

public class StateSymbolReference extends StateSymbol implements SymbolReference<StateSymbol> {
  
  private final SymbolReference<StateSymbol> reference;
  
  public StateSymbolReference(final String name, final Scope definingScopeOfReference) {
    super(name);
    
    reference = new CommonSymbolReference<>(name, StateSymbol.KIND, definingScopeOfReference);
  }
  
  @Override
  public StateSymbol getReferencedSymbol() {
    return reference.getReferencedSymbol();
  }
  
  @Override
  public boolean existsReferencedSymbol() {
    return reference.existsReferencedSymbol();
  }
  
  @Override
  public String getName() {
    return getReferencedSymbol().getName();
  }
  
  @Override
  public void setInitial(boolean isInitial) {
    getReferencedSymbol().setInitial(isInitial);
  }
  
  @Override
  public boolean isInitial() {
    return getReferencedSymbol().isInitial();
  }
  
  @Override
  public void setFinal(boolean isFinal) {
    getReferencedSymbol().setFinal(isFinal);
  }
  
  @Override
  public boolean isFinal() {
    return getReferencedSymbol().isFinal();
  }
}
