/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata._ast.ASTAutomaton;
import automata._cocos.AutomataCoCoChecker;
import automata.lang.AbstractTest;
import de.monticore.cocos.helper.Assert;
import de.se_rwth.commons.SourcePosition;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class AutomataCoCosTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
  }
  
  @Before
  public void setUp() throws RecognitionException, IOException {
    Log.getFindings().clear();
  }
  
  @Test
  public void testWithChecker() throws IOException {
    ASTAutomaton ast = // ...
    parseModel("src/test/resources/automata/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomataCoCoChecker checker = new AutomataCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    
    checker.checkAll(ast);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.warning("0xAUT02 State name 'notCapital' should start with a capital letter."
            , new SourcePosition(5, 2))
        );
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
  @Test
  public void testStateDoesNotStartWithCapitalLetter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automata/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.warning("0xAUT02 State name 'notCapital' should start with a capital letter."
            , new SourcePosition(5, 2))
        );
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
