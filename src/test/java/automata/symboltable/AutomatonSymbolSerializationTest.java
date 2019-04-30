package automata.symboltable;
/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */

import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import automata._symboltable.AutomataLanguage;
import automata._symboltable.AutomatonSymbol;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Symbol;


public class AutomatonSymbolSerializationTest {

  private GlobalScope globalScope;

  @Before
  public void init() {
    AutomataLanguage lang = new AutomataLanguage();
    ResolvingConfiguration resolverConfig = new ResolvingConfiguration();
    resolverConfig.addDefaultFilters(lang.getResolvingFilters());
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources"));
    globalScope = new GlobalScope(modelPath, lang, resolverConfig);
  }
  
  @Ignore("Ignore while symbol serialization is refactored")
  @Test
  public void loadSymbols() {
    Optional<Symbol> scope = globalScope.resolve("PingPong", AutomatonSymbol.KIND);
    assertTrue(scope.isPresent());
  }
  
}
