/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import de.monticore.symboltable.CommonSymbol;
import de.monticore.symboltable.SymbolKind;

public class TransitionSymbol extends CommonSymbol {
  
  public static final TransitionKind KIND = new TransitionKind();
  
  private final StateSymbol from;
  
  private final StateSymbol to;
  
  public TransitionSymbol(final String name, final StateSymbol from, final StateSymbol to) {
    super(name, KIND);
    this.from = from;
    this.to = to;
  }
  
  public StateSymbol getFrom() {
    return from;
  }
  
  public StateSymbol getTo() {
    return to;
  }
  
  public String getActivate() {
    return getName();
  }
  
  static final class TransitionKind extends SymbolKind {
    TransitionKind() {
    }
  }
}
