/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import de.monticore.symboltable.CommonSymbolTableCreator;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;

import javax.annotation.Nullable;

public class CommonAutomatonSymbolTableCreator extends CommonSymbolTableCreator implements
    AutomatonSymbolTableCreator {

  public CommonAutomatonSymbolTableCreator(ResolverConfiguration resolverConfig, @Nullable
  MutableScope enclosingScope) {
    super(resolverConfig, enclosingScope);
  }


}
