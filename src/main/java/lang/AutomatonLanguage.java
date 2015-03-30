/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package lang;

import javax.annotation.Nullable;

import symboltable.AutomatonModelLoader;
import symboltable.AutomatonSymbol;
import symboltable.AutomatonSymbolTableCreator;
import symboltable.CommonAutomatonSymbolTableCreator;
import symboltable.StateSymbol;
import symboltable.TransitionSymbol;
import _parser.AutomatonMCParser;
import _parser.AutomatonParserFactory;

import com.google.common.base.Optional;

import de.monticore.AbstractModelingLanguage;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.resolving.CommonResolvingFilter;

public class AutomatonLanguage extends AbstractModelingLanguage {
  public static final String FILE_ENDING = "aut";
  
  public AutomatonLanguage() {
    super("Automaton Language", FILE_ENDING, AutomatonSymbol.KIND);
    
    addResolver(CommonResolvingFilter.create(AutomatonSymbol.class, AutomatonSymbol.KIND));
    addResolver(CommonResolvingFilter.create(StateSymbol.class, StateSymbol.KIND));
    addResolver(CommonResolvingFilter.create(TransitionSymbol.class, TransitionSymbol.KIND));
  }
  
  @Override
  public AutomatonMCParser getParser() {
    return AutomatonParserFactory.createAutomatonMCParser();
  }
  
  @Override
  public Optional<AutomatonSymbolTableCreator> getSymbolTableCreator(
      ResolverConfiguration resolverConfiguration, @Nullable MutableScope enclosingScope) {
    return Optional
        .of(new CommonAutomatonSymbolTableCreator(resolverConfiguration, enclosingScope));
  }
  
  @Override
  public AutomatonModelLoader getModelLoader() {
    return (AutomatonModelLoader) super.getModelLoader();
  }
  
  @Override
  protected AutomatonModelLoader provideModelLoader() {
    return new AutomatonModelLoader(this);
  }
}
