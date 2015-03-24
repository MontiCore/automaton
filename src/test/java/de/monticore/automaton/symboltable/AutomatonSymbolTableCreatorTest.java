/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.symboltable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import de.monticore.automaton.AutomatonLanguage;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;

public class AutomatonSymbolTableCreatorTest {
  
  private Scope globalScope;
  
  @Before
  public void setup() {
    final AutomatonLanguage automatonLanguage = new AutomatonLanguage();
    
    final ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(automatonLanguage.getResolvers());
    
    final ModelPath modelPath =
        new ModelPath(Paths.get("src/test/resources/de/monticore/automaton/symboltable"));
    
    globalScope = new GlobalScope(modelPath, automatonLanguage.getModelLoader(),
        resolverConfiguration);
  }
  
  @Test
  public void testAutomatonSymbolTableCreation() {
    final AutomatonSymbol automatonSymbol =
        globalScope.<AutomatonSymbol> resolve("PingPong", AutomatonSymbol.KIND).orNull();
    
    assertNotNull(automatonSymbol);
    assertEquals("PingPong", automatonSymbol.getName());
    assertEquals("PingPong", automatonSymbol.getFullName());
    assertEquals(3, automatonSymbol.getStates().size());
    assertEquals(5, automatonSymbol.getTransitions().size());
    assertSame(automatonSymbol, automatonSymbol.getAstNode().get().getSymbol().get());
    assertSame(automatonSymbol.getDefiningScope(), automatonSymbol.getAstNode().get()
        .getEnclosingScope().get());
    
    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orNull();
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertTrue(noGameState.isInitial());
    assertFalse(noGameState.isFinal());
    assertSame(noGameState, noGameState.getAstNode().get().getSymbol().get());
    assertSame(noGameState.getDefiningScope(), noGameState.getAstNode().get().getEnclosingScope()
        .get());
    
    final StateSymbol pingState = automatonSymbol.getState("Ping").orNull();
    assertNotNull(pingState);
    assertEquals("Ping", pingState.getName());
    assertFalse(pingState.isInitial());
    assertFalse(pingState.isFinal());
    
    final StateSymbol pongState = automatonSymbol.getState("Pong").orNull();
    assertNotNull(pongState);
    assertEquals("Pong", pongState.getName());
    assertFalse(pongState.isInitial());
    assertFalse(pongState.isFinal());
    
    final TransitionSymbol noGame2Ping = automatonSymbol.getTransitions().get(0);
    assertEquals("startGame", noGame2Ping.getActivate());
    assertEquals("NoGame", noGame2Ping.getFrom().getName());
    assertTrue(noGame2Ping.getFrom().isInitial());
    assertEquals("Ping", noGame2Ping.getTo().getName());
    assertFalse(noGame2Ping.getTo().isInitial());
    
    // Test reference
    final StateSymbolReference fromReference = (StateSymbolReference) noGame2Ping.getFrom();
    assertSame(noGameState, fromReference.getReferencedSymbol());
    
  }
  
}
