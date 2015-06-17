/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.lang;

import java.util.Optional;

import javax.annotation.Nullable;

import automaton._parser.AutomatonMCParser;
import automaton._parser.AutomatonParserFactory;
import automaton.symboltable.AutomatonModelLoader;
import automaton.symboltable.AutomatonSymbol;
import automaton.symboltable.AutomatonSymbolTableCreator;
import automaton.symboltable.StateSymbol;
import de.monticore.CommonModelingLanguage;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.resolving.CommonResolvingFilter;

public class AutomatonLanguage extends CommonModelingLanguage {
  public static final String FILE_ENDING = "aut";
  
  public AutomatonLanguage() {
    super("Automaton Language", FILE_ENDING, AutomatonSymbol.KIND);
    
    addResolver(CommonResolvingFilter.create(AutomatonSymbol.class, AutomatonSymbol.KIND));
    addResolver(CommonResolvingFilter.create(StateSymbol.class, StateSymbol.KIND));
  }
  
  @Override
  public AutomatonMCParser getParser() {
    return AutomatonParserFactory.createAutomatonMCParser();
  }
  
  @Override
  public Optional<AutomatonSymbolTableCreator> getSymbolTableCreator(
      ResolverConfiguration resolverConfiguration, @Nullable MutableScope enclosingScope) {
    return Optional
        .of(new AutomatonSymbolTableCreator(resolverConfiguration, enclosingScope));
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
