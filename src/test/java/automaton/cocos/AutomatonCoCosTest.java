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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import automaton.AbstractTest;
import automaton._ast.ASTAutomaton;
import automaton._cocos.AutomatonCoCoChecker;
import de.monticore.cocos.CoCoHelper;
import de.monticore.cocos.LogMock;
import de.monticore.cocos.helper.Assert;
import de.se_rwth.commons.logging.Log;

public class AutomatonCoCosTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    LogMock.init();
    Log.enableFailQuick(false);
  }
  
  @Before
  public void setUp() {
    LogMock.getFindings().clear();
  }
  
  @Test
  public void testWithChecker() throws IOException {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    
    checker.checkAll(automaton);
    
    Collection<String> expectedErrors = Arrays.asList(
        CoCoHelper.buildErrorMsg("0xAUT02", "State name 'notCapital' should start with a capital "
            + "letter.",
            new SourcePosition(3, 2))
        );
    Assert.assertErrors(expectedErrors, LogMock.getFindings());
  }
  
  @Test
  public void testStateDoesNotStartWithCapitalLetter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    Collection<String> expectedErrors = Arrays.asList(
        CoCoHelper.buildErrorMsg(
            "0xAUT02", "State name 'notCapital' should start with a capital letter.",
            new SourcePosition(3, 2))
        );
    
    Assert.assertErrors(expectedErrors, LogMock.getFindings());
  }
  
}
