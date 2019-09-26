/* (c) https://github.com/MontiCore/monticore */
package automata.symboltable;

import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._symboltable.*;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

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
    assertSame(automatonSymbol, automatonSymbol.getAstNode().getSymbol());
    assertSame(automatonSymbol.getEnclosingScope(), automatonSymbol.getAstNode()
            .getEnclosingScope());

    final StateSymbol noGameState = automatonSymbol.getState("NoGame").orElse(null);
    assertNotNull(noGameState);
    assertEquals("NoGame", noGameState.getName());
    assertSame(noGameState, noGameState.getAstNode().getSymbol());
    assertSame(noGameState.getEnclosingScope(), noGameState.getAstNode().getEnclosingScope());

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
