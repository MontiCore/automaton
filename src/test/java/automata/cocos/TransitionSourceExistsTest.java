/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata._ast.ASTTransition;
import automata._cocos.AutomataCoCoChecker;
import automata._symboltable.*;
import automata.lang.AbstractTest;
import de.monticore.cocos.helper.Assert;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.SourcePosition;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransitionSourceExistsTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
  }
  
  @Before
  public void setUp() throws RecognitionException {
    LogStub.init();
    Log.getFindings().clear();
  }
  
  @Test
  public void testValid() {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automata/cocos/valid"));
    IAutomataScope globalScope = new AutomataGlobalScope(modelPath, "aut");

    Optional<AutomatonSymbol> automatonSymbol = globalScope.resolveAutomaton("A");
    assertTrue(automatonSymbol.isPresent());
    assertTrue(automatonSymbol.get().isPresentAstNode());
    ASTAutomaton automaton = automatonSymbol.get().getAstNode();
    
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    //The error log would be empty if automaton A was created from AST before being resolved
    assertEquals(1,Log.getFindings().size());
    assertTrue(Log.getFindings().stream().findFirst().get().isWarning());
  }
  
  @Test
  public void testNotExistingTransitionSource() {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automata/cocos/invalid"));
    ASTAutomaton ast = parseModel("src/test/resources/automata/cocos/invalid/NotExistingTransitionSource.aut");

    AutomataMill
        .automataSymbolTableCreatorBuilder()
        .addToScopeStack(new AutomataGlobalScope(modelPath, "aut"))
        .build()
        .createFromAST(ast);
    
    ASTTransition transition = ast.getTransitionsList().get(0);
    
    TransitionSourceExists coco = new TransitionSourceExists();
    coco.check(transition);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.error("0xAUT03 The source state of the transition does not exist.",
            new SourcePosition(6, 2))
        );
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
