/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import de.monticore.symboltable.CommonScopeSpanningSymbol;
import de.monticore.symboltable.SymbolKind;

public class AutomatonSymbol extends CommonScopeSpanningSymbol {

  public static final AutomatonKind KIND = new AutomatonKind();

  public AutomatonSymbol(final String name) {
    super(name, KIND);
  }

  public static final class AutomatonKind extends SymbolKind {
    private AutomatonKind() {

    }
  }
}
