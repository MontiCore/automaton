/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import mc.ast.SourcePosition;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import automaton._ast.ASTAutomaton;
import automaton._cocos.AutomatonCoCoChecker;
import automaton.lang.AbstractTest;
import de.monticore.cocos.CoCoFinding;
import de.monticore.cocos.CoCoLog;
import de.monticore.cocos.helper.Assert;

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
            "notCapital")
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
            "notCapital")
            , new SourcePosition(3, 2))
        );
    
    Assert.assertErrors(expectedErrors, CoCoLog.getFindings());
  }
  
}
