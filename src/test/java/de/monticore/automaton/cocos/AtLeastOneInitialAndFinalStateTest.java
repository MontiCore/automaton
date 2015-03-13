/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.cocos;

import com.google.common.base.Optional;

import de.monticore.automaton.AbstractTest;
import de.monticore.automaton._ast.ASTAutomaton;
import de.monticore.automaton._parser.AutomatonMCParser;
import de.monticore.cocos.CoCoHelper;
import de.monticore.cocos.LogMock;
import de.monticore.cocos.helper.Assert;
import de.se_rwth.commons.logging.Log;
import mc.ast.SourcePosition;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class AtLeastOneInitialAndFinalStateTest extends AbstractTest {

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
  public void testValid() throws IOException {
    ASTAutomaton automaton = parseModel("src/test/resources/de/monticore/automaton/cocos/valid/A.aut");
    AtLeastOneInitialAndFinalState coco = new AtLeastOneInitialAndFinalState();
    coco.check(automaton);

    assertTrue(LogMock.getFindings().isEmpty());
  }

  @Test
  public void testMissingInitialState() throws IOException {
    ASTAutomaton automaton = parseModel("src/test/resources/de/monticore/automaton/cocos/invalid/MissingInitialState.aut");

    AtLeastOneInitialAndFinalState coco = new AtLeastOneInitialAndFinalState();
    coco.check(automaton);

    Collection<String> expectedErrors = Arrays.asList(
        CoCoHelper.buildErrorMsg(
            "0xAUT001", "An automaton must have at least one initial and one final state.",
            new SourcePosition(1, 0))
    );

    Assert.assertErrors(expectedErrors, LogMock.getFindings());
  }

}