/* (c) https://github.com/MontiCore/monticore */
package automata.symboltable;

import automata.AutomataTool;
import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata._symboltable.AutomataScopesGenitor;
import automata._symboltable.IAutomataGlobalScope;
import automata._symboltable.IAutomataScope;
import automata._visitor.AutomataTraverser;
import de.monticore.io.paths.MCPath;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

public class AutomatonSymbolTableCreatorTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }

  private IAutomataGlobalScope globalScope;

  @Before
  public void setup() {
    final MCPath symbolPath =
            new MCPath(Paths.get("src/test/resources/automata/symboltable"));

    globalScope = AutomataMill.globalScope();
    globalScope.clear();
    globalScope.setSymbolPath(symbolPath);
    
    Log.enableFailQuick(false);
  }

  @Test
  public void testAutomatonSymbolTableCreation2(){
    ASTAutomaton ast = new AutomataTool()
            .parse("src/test/resources/automata/symboltable/PingPong.aut");
    AutomataScopesGenitor genitor = AutomataMill.scopesGenitor();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.setAutomataHandler(genitor);
    traverser.add4Automata(genitor);
    genitor.putOnStack(globalScope);
    IAutomataScope artifact = genitor.createFromAST(ast);

    IAutomataScope s = artifact.getSubScopes().stream().findAny().get();
    assertTrue(s.resolveState("NoGame").isPresent());
    assertTrue(s.resolveState("PingPong.NoGame").isPresent());
    assertTrue(s.resolveAutomaton("PingPong").isPresent());

    assertTrue(globalScope.resolveAutomaton("PingPong").isPresent());
    assertTrue(globalScope.resolveState("PingPong.NoGame").isPresent());

    assertTrue(artifact.resolveAutomaton("PingPong").isPresent());
    assertTrue(artifact.resolveState("PingPong.NoGame").isPresent());

  }

}
