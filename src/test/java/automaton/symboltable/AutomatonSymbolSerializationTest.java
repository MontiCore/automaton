package automaton.symboltable;
/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */

import automaton.AutomatonTool;
import automaton._symboltable.AutomatonLanguage;
import automaton._symboltable.AutomatonSymbol;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Symbol;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertTrue;


public class AutomatonSymbolSerializationTest {

  private GlobalScope globalScope;

  @Before
  public void init() {
    AutomatonLanguage lang = new AutomatonLanguage();
    ResolvingConfiguration resolverConfig = new ResolvingConfiguration();
    resolverConfig.addDefaultFilters(lang.getResolvingFilters());
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources"));
    globalScope = new GlobalScope(modelPath, lang, resolverConfig);
  }
  
  @Test
  public void loadSymbols() {
    Optional<Symbol> scope = globalScope.resolve("PingPong", AutomatonSymbol.KIND);
    assertTrue(scope.isPresent());
  }
  
}
