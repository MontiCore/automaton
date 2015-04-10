/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import _ast.ASTAutomaton;
import _cocos.AutomatonCoCoChecker;
import de.monticore.cocos.CoCoFinding;
import de.monticore.cocos.CoCoLog;
import de.monticore.cocos.helper.Assert;
import lang.AbstractTest;
import mc.ast.SourcePosition;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class AutomatonCoCosTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    CoCoLog.setDelegateToLog(false);
  }
  
  @Before
  public void setUp() throws RecognitionException, IOException {
    CoCoLog.getFindings().clear();
  }
  
  @Test
  public void testWithChecker() throws IOException {
    ASTAutomaton ast = // ...
        parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());

    checker.checkAll(ast);
    
    Collection<CoCoFinding> expectedErrors = Arrays.asList(
        CoCoFinding.warning(StateNameStartsWithCapitalLetter.ERROR_CODE, String.format(
            StateNameStartsWithCapitalLetter.ERROR_MSG_FORMAT,
            "notCapital\n  //custom ASTState node\n ")
            , new SourcePosition(3, 2))
        );
    Assert.assertErrors(expectedErrors, CoCoLog.getFindings());
  }
  
  @Test
  public void testStateDoesNotStartWithCapitalLetter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    Collection<CoCoFinding> expectedErrors = Arrays.asList(
        CoCoFinding.warning(StateNameStartsWithCapitalLetter.ERROR_CODE, String.format(
            StateNameStartsWithCapitalLetter.ERROR_MSG_FORMAT,
            "notCapital\n  //custom ASTState node\n ")
            , new SourcePosition(3, 2))
        );
    
    Assert.assertErrors(expectedErrors, CoCoLog.getFindings());
  }
  
}
