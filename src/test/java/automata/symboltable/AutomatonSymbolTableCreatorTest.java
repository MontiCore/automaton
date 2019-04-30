/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.symboltable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import automata._symboltable.AutomataLanguage;
import automata._symboltable.AutomatonSymbol;
import automata._symboltable.StateSymbol;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;

public class AutomatonSymbolTableCreatorTest {
  
  private Scope globalScope;
  
  @Before
  public void setup() {
    final AutomataLanguage automataLanguage = new AutomataLanguage();
    
    final ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
    resolverConfiguration.addDefaultFilters(automataLanguage.getResolvingFilters());
    
    final ModelPath modelPath =
        new ModelPath(Paths.get("src/test/resources/automata/symboltable"));
    
    globalScope = new GlobalScope(modelPath, automataLanguage, resolverConfiguration);
  }
  
  @Test
  public void testAutomatonSymbolTableCreation() {
    final AutomatonSymbol automatonSymbol =
        globalScope.<AutomatonSymbol> resolve("PingPong", AutomatonSymbol.KIND).orElse(null);
    
    assertNotNull(automatonSymbol);
    assertEquals("PingPong", automatonSymbol.getName());
    assertEquals("PingPong", automatonSymbol.getFullName());
    assertEquals(3, automatonSymbol.getStates().size());
    assertSame(automatonSymbol, automatonSymbol.getAstNode().get().getSymbol());
    assertSame(automatonSymbol.getEnclosingScope(), automatonSymbol.getAstNode().get()
        .getEnclosingScope());
    
    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orElse(null);
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertSame(noGameState, noGameState.getAstNode().get().getSymbol());
    assertSame(noGameState.getEnclosingScope(), noGameState.getAstNode().get().getEnclosingScope());
    
    final StateSymbol pingState = automatonSymbol.getState("Ping").orElse(null);
    assertNotNull(pingState);
    assertEquals("Ping", pingState.getName());
     
    final StateSymbol pongState = automatonSymbol.getState("Pong").orElse(null);
    assertNotNull(pongState);
    assertEquals("Pong", pongState.getName());
    
  }
  
}
