/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package cocos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import lang.AbstractTest;
import lang.AutomatonLanguage;
import mc.ast.SourcePosition;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import symboltable.AutomatonSymbol;
import symboltable.AutomatonSymbolTableCreator;
import _ast.ASTAutomaton;
import _ast.ASTTransition;
import _cocos.AutomatonCoCoChecker;

import com.google.common.base.Optional;

import de.monticore.ModelingLanguage;
import de.monticore.cocos.CoCoFinding;
import de.monticore.cocos.CoCoLog;
import de.monticore.cocos.helper.Assert;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;

public class ExistingTransitionSourceTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    CoCoLog.setDelegateToLog(false);
  }
  
  @Before
  public void setUp() throws RecognitionException, IOException {
    CoCoLog.getFindings().clear();
  }
  
  @Test
  public void testValid() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automaton/cocos/valid"));
    ModelingLanguage language = new AutomatonLanguage();
    ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(language.getResolvers());
    Scope globalScope = new GlobalScope(modelPath, language.getModelLoader(), resolverConfiguration);
    
    Optional<AutomatonSymbol> automatonSymbol = globalScope.resolve("A", AutomatonSymbol.KIND);
    assertTrue(automatonSymbol.isPresent());
    ASTAutomaton automaton = (ASTAutomaton) automatonSymbol.get().getAstNode().get();
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    assertTrue(CoCoLog.getFindings().isEmpty());
  }
  
  @Test
  public void testNotExistingTransitionSource() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automaton/cocos/invalid"));
    AutomatonLanguage language = new AutomatonLanguage();
    ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(language.getResolvers());
    GlobalScope globalScope = new GlobalScope(modelPath, language.getModelLoader(),
        resolverConfiguration);
    
    ASTAutomaton ast = parseModel("src/test/resources/automaton/cocos/invalid/NotExistingTransitionSource.aut");
    
    Optional<AutomatonSymbolTableCreator> stc = language.getSymbolTableCreator(
        resolverConfiguration, globalScope);
    stc.get().createFromAST(ast);
    
    ASTTransition transition = ast.getTransitions().get(0);
    
    ExistingTransitionSource coco = new ExistingTransitionSource();
    coco.check(transition);
    
    Collection<CoCoFinding> expectedErrors = Arrays.asList(
        CoCoFinding.error(ExistingTransitionSource.ERROR_CODE,
            ExistingTransitionSource.ERROR_MSG_FORMAT, new SourcePosition(4, 2))
        );
    
    Assert.assertErrors(expectedErrors, CoCoLog.getFindings());
  }
  
}
