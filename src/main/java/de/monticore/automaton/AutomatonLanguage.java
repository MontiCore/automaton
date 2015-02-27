/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton;

import com.google.common.base.Optional;
import de.cd4analysis._parser.AutomatonMCParser;
import de.monticore.AbstractModelingLanguage;
import de.monticore.automaton.symboltable.AutomatonSymbol;
import de.monticore.automaton.symboltable.AutomatonSymbolTableCreator;
import de.monticore.automaton.symboltable.CommonAutomatonSymbolTableCreator;
import de.monticore.automaton.symboltable.StateSymbol;
import de.monticore.automaton.symboltable.TransitionSymbol;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.resolving.CommonResolvingFilter;

import javax.annotation.Nullable;

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
    return new AutomatonMCParser();
  }
  
  @Override
  public Optional<AutomatonSymbolTableCreator> getSymbolTableCreator(
      ResolverConfiguration resolverConfiguration, @Nullable MutableScope enclosingScope) {
    return Optional.of(new CommonAutomatonSymbolTableCreator(resolverConfiguration, enclosingScope));
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
