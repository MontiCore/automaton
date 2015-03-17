/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package de.monticore.automaton.cocos;

import com.google.common.base.Optional;
import de.monticore.ModelingLanguage;
import de.monticore.automaton.AbstractTest;
import de.monticore.automaton.AutomatonLanguage;
import de.monticore.automaton._ast.ASTAutomaton;
import de.monticore.automaton._ast.ASTTransition;
import de.monticore.automaton._cocos.AutomatonCoCoChecker;
import de.monticore.automaton.symboltable.AutomatonSymbol;
import de.monticore.cocos.CoCoHelper;
import de.monticore.cocos.LogMock;
import de.monticore.cocos.helper.Assert;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;
import mc.ast.SourcePosition;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class ExistingTransitionSourceTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    LogMock.init();
    Log.enableFailQuick(false);
  }
  
  @Before
  public void setUp() {
    LogMock.getFindings().clear();
  }

  // TODO PN, RH: The symbol table does a lot of logging. So, LogMock.getFindings().isEmpty() is
  // never true. => Find a solution for this.
  
  @Test
  @Ignore
  public void testValid() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/de/monticore/automaton/cocos/valid"));
    ModelingLanguage language = new AutomatonLanguage();
    ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(language.getResolvers());
    Scope globalScope = new GlobalScope(modelPath, language.getModelLoader(), resolverConfiguration);

    Optional<AutomatonSymbol> automatonSymbol = globalScope.resolve("A", AutomatonSymbol.KIND);
    assertTrue(automatonSymbol.isPresent());
    ASTAutomaton automaton = (ASTAutomaton) automatonSymbol.get().getAstNode().get();

    LogMock.getFindings().clear();

    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);

    assertTrue(LogMock.getFindings().isEmpty());
  }
  
  @Test
  @Ignore
  public void testNotExistingTransitionSource() throws IOException {
    ASTTransition transition =
        parseModel("src/test/resources/de/monticore/automaton/cocos/invalid/NotExistingTransitionSource.aut")
          .getTransitions().get(0);

    
    ExistingTransitionSource coco = new ExistingTransitionSource();
    coco.check(transition);
    
    Collection<String> expectedErrors = Arrays.asList(
        CoCoHelper.buildErrorMsg(
            ExistingTransitionSource.ERROR_CODE, ExistingTransitionSource.ERROR_MSG_FORMAT,
            new SourcePosition(1, 0))
        );
    
    Assert.assertErrors(expectedErrors, LogMock.getFindings());
  }
  
}
