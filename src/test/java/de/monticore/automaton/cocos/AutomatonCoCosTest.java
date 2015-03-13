/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.cocos;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import mc.ast.SourcePosition;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.monticore.automaton.AbstractTest;
import de.monticore.automaton._ast.ASTAutomaton;
import de.monticore.automaton._cocos.AutomatonCoCoChecker;
import de.monticore.cocos.CoCoHelper;
import de.monticore.cocos.LogMock;
import de.monticore.cocos.helper.Assert;
import de.se_rwth.commons.logging.Log;

public class AutomatonCoCosTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    LogMock.init();
    Log.enableFailQuick(false);
    LogMock.setProduceOutput(false);
  }
  
  @Before
  public void setUp() {
    LogMock.getFindings().clear();
  }
  
  @Test
  public void testWithChecker() throws IOException {
    ASTAutomaton automaton = parseModel("src/test/resources/de/monticore/automaton/cocos/invalid"
        + "/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = AutomatonCoCos.getCheckerForAllCoCos();
    
    Collection<String> expectedErrors = Arrays.asList(
        CoCoHelper.buildErrorMsg("0xAUT002", "State name 'notCapital' should start with a capital "
            + "letter.",
            new SourcePosition(3, 2))
        );
    checker.checkAll(automaton);
    Assert.assertErrors(expectedErrors, LogMock.getFindings());
  }
}
