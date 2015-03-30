/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import de.monticore.symboltable.CommonSymbolTableCreator;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;

public class CommonAutomatonSymbolTableCreator extends CommonSymbolTableCreator implements
    AutomatonSymbolTableCreator {
  
  public CommonAutomatonSymbolTableCreator(final ResolverConfiguration resolverConfig,
      final MutableScope enclosingScope) {
    super(resolverConfig, enclosingScope);
  }
  
}
