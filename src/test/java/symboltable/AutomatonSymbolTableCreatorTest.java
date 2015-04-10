/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package symboltable;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import lang.AutomatonLanguage;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

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

  @Ignore("Fixed for (at least) MC 4.1.2-SNAPSHOT")
  @Test
  public void testAutomatonSymbolTableCreation() {
    final AutomatonSymbol automatonSymbol =
        globalScope.<AutomatonSymbol> resolve("PingPong", AutomatonSymbol.KIND).orElse(null);
    
    assertNotNull(automatonSymbol);
    assertEquals("PingPong", automatonSymbol.getName());
    assertEquals("PingPong", automatonSymbol.getFullName());
    assertEquals(3, automatonSymbol.getStates().size());
    assertEquals(5, automatonSymbol.getTransitions().size());
    assertSame(automatonSymbol, automatonSymbol.getAstNode().get().getSymbol().get());
    assertSame(automatonSymbol.getEnclosingScope(), automatonSymbol.getAstNode().get()
        .getEnclosingScope().get());
    
    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orElse(null);
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertTrue(noGameState.isInitial());
    assertFalse(noGameState.isFinal());
    assertSame(noGameState, noGameState.getAstNode().get().getSymbol().get());
    assertSame(noGameState.getEnclosingScope(), noGameState.getAstNode().get().getEnclosingScope().get());
    
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
