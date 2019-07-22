/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.symboltable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._symboltable.AutomataGlobalScope;
import automata._symboltable.AutomataLanguage;
import automata._symboltable.AutomataScope;
import automata._symboltable.AutomataSymbolTableCreator;
import automata._symboltable.AutomatonSymbol;
import automata._symboltable.IAutomataScope;
import automata._symboltable.StateSymbol;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.logging.Log;

public class AutomatonSymbolTableCreatorTest {

  private IAutomataScope globalScope;

  @Before
  public void setup() {
    final AutomataLanguage automataLanguage = new AutomataLanguage();

    final ModelPath modelPath =
            new ModelPath(Paths.get("src/test/resources/automata/symboltable"));

    globalScope = new AutomataGlobalScope(modelPath, automataLanguage);
    Log.enableFailQuick(false);
  }

  @Test
  public void testAutomatonSymbolTableCreation() {
    final AutomatonSymbol automatonSymbol =
            globalScope.resolveAutomaton("PingPong").orElse(null);

    assertNotNull(automatonSymbol);
    assertEquals("PingPong", automatonSymbol.getName());
    assertEquals("PingPong", automatonSymbol.getFullName());
    assertEquals(3, automatonSymbol.getStates().size());
    assertSame(automatonSymbol, automatonSymbol.getAstNode().get().getSymbol2());
    assertSame(automatonSymbol.getEnclosingScope(), automatonSymbol.getAstNode().get()
            .getEnclosingScope2());

    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orElse(null);
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertSame(noGameState, noGameState.getAstNode().get().getSymbol2());
    assertSame(noGameState.getEnclosingScope(), noGameState.getAstNode().get().getEnclosingScope2());

    final StateSymbol pingState = automatonSymbol.getState("Ping").orElse(null);
    assertNotNull(pingState);
    assertEquals("Ping", pingState.getName());

    final StateSymbol pongState = automatonSymbol.getState("Pong").orElse(null);
    assertNotNull(pongState);
    assertEquals("Pong", pongState.getName());

  }

  @Test
  public void testAutomatonSymbolTableCreation2(){
    ASTAutomaton ast = AutomataTool
            .parse("src/test/resources/automata/symboltable/PingPong.aut");
    final AutomataLanguage automataLanguage = new AutomataLanguage();

    AutomataScope myglobal = new AutomataGlobalScope(new ModelPath(Paths.get("src/main/resources/example")),automataLanguage);
    AutomataSymbolTableCreator stcreator = new AutomataSymbolTableCreator(myglobal);
    IAutomataScope artifact = stcreator.createFromAST(ast);
    IAutomataScope s = artifact.getSubScopes().stream().findAny().get();
    assertTrue(s.resolveState("NoGame").isPresent());
    assertTrue(s.resolveAutomaton("PingPong").isPresent());
    assertTrue(artifact.resolveAutomaton("PingPong").isPresent());
    assertTrue(artifact.resolveState("PingPong.NoGame").isPresent());

    assertTrue(myglobal.resolveAutomaton("PingPong").isPresent());
    assertTrue(myglobal.resolveState("PingPong.NoGame").isPresent());

    assertTrue(s.resolveState("PingPong.NoGame").isPresent());

  }

}
