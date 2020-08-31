/* (c) https://github.com/MontiCore/monticore */
package automata.symboltable;

import automata.AutomataMill;
import automata.AutomataTool;
import automata._ast.ASTAutomaton;
import automata._symboltable.*;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.logging.Log;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.*;

public class AutomatonSymbolSerializationTest {

  private IAutomataArtifactScope scope;

  public void setup(String model) {
    Log.enableFailQuick(false);
    ASTAutomaton ast = AutomataTool.parse("src/test/resources/" + model);

    ModelPath mp = new ModelPath(Paths.get("src/test/resources/" + model).getParent());
    AutomataGlobalScope globalScope = AutomataMill.automataGlobalScopeBuilder()
        .setModelPath(mp).setModelFileExtension("aut").build();
    scope = AutomataMill.automataSymbolTableCreatorBuilder()
        .addToScopeStack(globalScope).build().createFromAST(ast);
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

  @Test
  public void testDoorModel() {
    String path = "automata/symboltable/Door.aut";
    String symbolPath = AutomataTool.DEFAULT_SYMBOL_LOCATION + "/Door.autsym";
    AutomataTool.main(new String[] { "src/test/resources/" + path });
    assertTrue(new File(symbolPath).exists());

    AutomataScopeDeSer deSer = new AutomataScopeDeSer();
    deSer.setSymbolFileExtension("autsym");
    AutomataArtifactScope artifactScope = deSer.load(symbolPath);
    assertEquals("Door",artifactScope.getName());
    assertEquals("",artifactScope.getPackageName());
    assertEquals(1, artifactScope.getSymbolsSize());
    assertEquals(1, artifactScope.getSubScopes().size());
    assertTrue(null == artifactScope.getEnclosingScope());

    AutomatonSymbol automatonSymbol = artifactScope.getLocalAutomatonSymbols().get(0);
    assertEquals("Door", automatonSymbol.getName());
    assertTrue(null != automatonSymbol.getSpannedScope());
    assertEquals(artifactScope, automatonSymbol.getEnclosingScope());

    IAutomataScope automatonScope = automatonSymbol.getSpannedScope();
    assertEquals("Door",automatonScope.getName());
    assertEquals(2, automatonScope.getSymbolsSize());
    assertEquals(2, automatonScope.getSubScopes().size());
    assertEquals(artifactScope, automatonSymbol.getEnclosingScope());

    StateSymbol close = automatonScope.getLocalStateSymbols().get(0);
    assertEquals("Close",close.getName());
    assertTrue(null != close.getSpannedScope());
    assertEquals(automatonScope, close.getEnclosingScope());

    StateSymbol open = automatonScope.getLocalStateSymbols().get(1);
    assertEquals("Open",open.getName());
    assertTrue(null != open.getSpannedScope());
    assertEquals(automatonScope, open.getEnclosingScope());

    IAutomataScope closeScope = close.getSpannedScope();
    assertEquals("Close",closeScope.getName());
    assertEquals(3, closeScope.getSymbolsSize());
    assertEquals(3, closeScope.getSubScopes().size());
    assertEquals(automatonScope, closeScope.getEnclosingScope());

    IAutomataScope openScope = open.getSpannedScope();
    assertEquals("Open",openScope.getName());
    assertEquals(0, openScope.getSymbolsSize());
    assertEquals(0, openScope.getSubScopes().size());
    assertEquals(automatonScope, openScope.getEnclosingScope());
  }

}
