/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import automaton._ast.ASTAutomaton;
import automaton._cocos.AutomatonCoCoChecker;
import automaton.lang.AbstractTest;
import de.monticore.cocos.helper.Assert;
import de.se_rwth.commons.SourcePosition;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;

public class AutomatonCoCosTest extends AbstractTest {
  
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
    parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialAndFinalState());
    checker.addCoCo(new StateNameStartsWithCapitalLetter());
    
    checker.checkAll(ast);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.warning("0xAUT02 State name 'notCapital' should start with a capital letter."
            , new SourcePosition(3, 2))
        );
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
  @Test
  public void testStateDoesNotStartWithCapitalLetter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automaton/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut");
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.warning("0xAUT02 State name 'notCapital' should start with a capital letter."
            , new SourcePosition(3, 2))
        );
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
