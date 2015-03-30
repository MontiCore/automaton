/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import de.monticore.symboltable.CommonSymbol;
import de.monticore.symboltable.SymbolKind;

public class StateSymbol extends CommonSymbol {
  
  public static final StateKind KIND = new StateKind();
  
  private boolean isInitial = false;
  
  private boolean isFinal = false;
  
  public StateSymbol(String name) {
    super(name, KIND);
  }
  
  public void setInitial(boolean isInitial) {
    this.isInitial = isInitial;
  }
  
  public boolean isInitial() {
    return isInitial;
  }
  
  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }
  
  public boolean isFinal() {
    return isFinal;
  }
  
  static final class StateKind extends SymbolKind {
    StateKind() {
    }
  }
}
