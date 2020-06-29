/* (c) https://github.com/MontiCore/monticore */
package automata.symboltable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._symboltable.*;
import de.monticore.io.paths.ModelPath;


public class AutomatonSymbolSerializationTest {

  private AutomataArtifactScope scope;

  public void setup(String model) {
    ASTAutomaton ast = AutomataTool
        .parse("src/test/resources/" + model);

    AutomataScope myglobal = new AutomataScope();
    AutomataSymbolTableCreator stcreator = new AutomataSymbolTableCreator(
        (IAutomataScope) myglobal);
    scope = stcreator.createFromAST(ast);
  }
  
  @Test
  public void testserializationOfAutomatonScope() {
    setup("automata/symboltable/PingPong.aut");
    final AutomatonSymbol automatonSymbol = scope.resolveAutomaton("PingPong").orElse(null);
    assertNotNull(automatonSymbol);

    AutomataScopeDeSer deser = new AutomataScopeDeSer();
    deser.setSymbolFileExtension("autsym");
    String serialized = deser.serialize(scope);
    System.out.println(serialized);
    assertTrue(serialized.length() > 0);

    IAutomataScope deserialized = deser.deserialize(serialized);
    assertNotNull(deserialized);
    assertEquals(1, deserialized.getLocalAutomatonSymbols().size());
    AutomatonSymbol autSymbol = (AutomatonSymbol) deserialized.getLocalAutomatonSymbols()
        .toArray()[0];
    assertEquals("PingPong", autSymbol.getName());
    assertEquals(1, deserialized.getSubScopes().size());
  }

  @Test
  public void testLoadSymbol() {
    ModelPath mp = new ModelPath(Paths.get("src/test/resources"));
    AutomataGlobalScope gs = new AutomataGlobalScope(mp, "aut");
    Optional<AutomatonSymbol> resolvedAutomaton = gs
        .resolveAutomaton("automata.serialization.Ping2Pong2");
    assertTrue(resolvedAutomaton.isPresent());
  }

  @Test
  public void testStoreSymbols() {
    String path = "automata/cocos/valid/A.aut";
    AutomataTool.main(new String[] { "src/test/resources/" + path });
    // Note: usually, the stored symbols will be in a file located in the folder structure that
    // matches the package structure. However, the automata language does not defines packages for
    // automata.
    assertTrue(
        new File(AutomataTool.DEFAULT_SYMBOL_LOCATION + "/A.autsym").exists());
  }
  
}
