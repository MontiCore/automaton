/* (c) https://github.com/MontiCore/monticore */
package automata.symboltable;

import automata.AutomataMill;
import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._symboltable.*;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class AutomatonSymbolTableCreatorTest {

  private IAutomataGlobalScope globalScope;

  @Before
  public void setup() {
    final ModelPath modelPath =
            new ModelPath(Paths.get("src/test/resources/automata/symboltable"));

    globalScope = AutomataMill.globalScope();
    globalScope.clear();
    globalScope.setFileExt("aut");
    globalScope.setModelPath(modelPath);
    
    Log.enableFailQuick(false);
  }

  @Ignore
  // TODO: MB Wenn das Laden von Modellen erlaubt wird, kann man den Test wieder einschalten
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
    AutomataSymbolTableCreatorDelegator stcreator = AutomataMill.automataSymbolTableCreatorDelegator();
    IAutomataScope artifact = stcreator.createFromAST(ast);
    IAutomataScope s = artifact.getSubScopes().stream().findAny().get();
    assertTrue(s.resolveState("NoGame").isPresent());
    assertTrue(s.resolveAutomaton("PingPong").isPresent());
    assertTrue(artifact.resolveAutomaton("PingPong").isPresent());
    assertTrue(artifact.resolveState("PingPong.NoGame").isPresent());

    assertTrue(globalScope.resolveAutomaton("PingPong").isPresent());
    assertTrue(globalScope.resolveState("PingPong.NoGame").isPresent());

    assertTrue(s.resolveState("PingPong.NoGame").isPresent());

  }

}
