/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import lang.AbstractTest;
import mc.ast.SourcePosition;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import _ast.ASTAutomaton;
import de.monticore.cocos.CoCoFinding;
import de.monticore.cocos.CoCoLog;
import de.monticore.cocos.helper.Assert;

public class AtLeastOneInitialAndFinalStateTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    CoCoLog.setDelegateToLog(false);
  }
  
  @Before
  public void setUp() throws RecognitionException, IOException {
    CoCoLog.getFindings().clear();
  }
  
  @Test
  public void testValid() {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/valid/A.aut");
    
    AtLeastOneInitialAndFinalState coco = new AtLeastOneInitialAndFinalState();
    coco.check(automaton);
    
    assertTrue(CoCoLog.getFindings().isEmpty());
  }
  
  @Test
  public void testMissingInitialState() throws IOException {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/invalid/MissingInitialState.aut");
    
    AtLeastOneInitialAndFinalState coco = new AtLeastOneInitialAndFinalState();
    coco.check(automaton);
    
    Collection<CoCoFinding> expectedErrors = Arrays.asList(
        CoCoFinding.error(AtLeastOneInitialAndFinalState.ERROR_CODE,
            AtLeastOneInitialAndFinalState.ERROR_MSG_FORMAT, new SourcePosition(1, 0))
        );
    
    Assert.assertErrors(expectedErrors, CoCoLog.getFindings());
  }
  
}
