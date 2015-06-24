/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.symboltable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import automaton.lang.AutomatonLanguage;

import org.junit.Before;
import org.junit.Test;

import automaton.symboltable.AutomatonSymbol;
import automaton.symboltable.StateSymbol;

public class AutomatonSymbolTableCreatorTest {
  
  private Scope globalScope;
  
  @Before
  public void setup() {
    final AutomatonLanguage automatonLanguage = new AutomatonLanguage();
    
    final ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(automatonLanguage.getResolvers());
    
    final ModelPath modelPath =
        new ModelPath(Paths.get("src/test/resources/automaton/symboltable"));
    
    globalScope = new GlobalScope(modelPath, automatonLanguage.getModelLoader(),
        resolverConfiguration);
  }
  
  @Test
  public void testAutomatonSymbolTableCreation() {
    final AutomatonSymbol automatonSymbol =
        globalScope.<AutomatonSymbol> resolve("PingPong", AutomatonSymbol.KIND).orElse(null);
    
    assertNotNull(automatonSymbol);
    assertEquals("PingPong", automatonSymbol.getName());
    assertEquals("PingPong", automatonSymbol.getFullName());
    assertEquals(3, automatonSymbol.getStates().size());
    assertSame(automatonSymbol, automatonSymbol.getAstNode().get().getSymbol().get());
    assertSame(automatonSymbol.getEnclosingScope(), automatonSymbol.getAstNode().get()
        .getEnclosingScope().get());
    
    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orElse(null);
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertTrue(noGameState.isInitial());
    assertFalse(noGameState.isFinal());
    assertSame(noGameState, noGameState.getAstNode().get().getSymbol().get());
    assertSame(noGameState.getEnclosingScope(), noGameState.getAstNode().get().getEnclosingScope()
        .get());
    
    final StateSymbol pingState = automatonSymbol.getState("Ping").orElse(null);
    assertNotNull(pingState);
    assertEquals("Ping", pingState.getName());
    assertFalse(pingState.isInitial());
    assertFalse(pingState.isFinal());
    
    final StateSymbol pongState = automatonSymbol.getState("Pong").orElse(null);
    assertNotNull(pongState);
    assertEquals("Pong", pongState.getName());
    assertFalse(pongState.isInitial());
    assertFalse(pongState.isFinal());
    
  }
  
}