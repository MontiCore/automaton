/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton.cocos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import automaton._ast.ASTAutomaton;
import automaton._ast.ASTTransition;
import automaton._cocos.AutomatonCoCoChecker;
import automaton._symboltable.AutomatonLanguage;
import automaton._symboltable.AutomatonSymbol;
import automaton._symboltable.AutomatonSymbolTableCreator;
import automaton.lang.AbstractTest;
import de.monticore.ModelingLanguage;
import de.monticore.cocos.helper.Assert;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.SourcePosition;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransitionSourceExistsTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
  }
  
  @Before
  public void setUp() throws RecognitionException, IOException {
    Log.getFindings().clear();
  }
  
  @Test
  public void testValid() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automaton/cocos/valid"));
    ModelingLanguage language = new AutomatonLanguage();
    ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(language.getResolvers());
    Scope globalScope = new GlobalScope(modelPath, language, resolverConfiguration);
    
    Optional<AutomatonSymbol> automatonSymbol = globalScope.resolve("A", AutomatonSymbol.KIND);
    assertTrue(automatonSymbol.isPresent());
    ASTAutomaton automaton = (ASTAutomaton) automatonSymbol.get().getAstNode().get();
    
    AutomatonCoCoChecker checker = new AutomatonCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    assertTrue(Log.getFindings().isEmpty());
  }
  
  @Test
  public void testNotExistingTransitionSource() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automaton/cocos/invalid"));
    AutomatonLanguage language = new AutomatonLanguage();
    ResolverConfiguration resolverConfiguration = new ResolverConfiguration();
    resolverConfiguration.addTopScopeResolvers(language.getResolvers());
    GlobalScope globalScope = new GlobalScope(modelPath, language, resolverConfiguration);
    
    ASTAutomaton ast = parseModel("src/test/resources/automaton/cocos/invalid/NotExistingTransitionSource.aut");
    
    Optional<AutomatonSymbolTableCreator> stc = language.getSymbolTableCreator(
        resolverConfiguration, globalScope);
    stc.get().createFromAST(ast);
    
    ASTTransition transition = ast.getTransitions().get(0);
    
    TransitionSourceExists coco = new TransitionSourceExists();
    coco.check(transition);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.error("0xAUT03 The source state of the transition does not exist.",
            new SourcePosition(4, 2))
        );
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
