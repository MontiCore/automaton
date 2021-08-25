/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata.AutomataMill;
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

import java.util.Collection;
import java.util.Collections;

public class AutomataCoCosTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }
  
  @Before
  public void setUp() throws RecognitionException {
    Log.getFindings().clear();
  }
  
  @Test
  public void testWithChecker(){
    ASTAutomaton ast = // ...
    parseModel("src/test/resources/automata/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomataCoCoChecker checker = new AutomataCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    
    checker.checkAll(ast);
    
    Collection<Finding> expectedErrors = Collections.singletonList(
      Finding.warning("0xB4002 State name 'notCapital' should start with a capital letter.",
        new SourcePosition(5, 2)));
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
  @Test
  public void testStateDoesNotStartWithCapitalLetter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automata/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    Collection<Finding> expectedErrors = Collections.singletonList(
      Finding.warning("0xB4002 State name 'notCapital' should start with a capital letter.",
        new SourcePosition(5, 2)));
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
