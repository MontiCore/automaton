/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata.cocos;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import automata._ast.ASTAutomaton;
import automata._ast.ASTTransition;
import automata._cocos.AutomataCoCoChecker;
import automata._symboltable.AutomataLanguage;
import automata._symboltable.AutomatonSymbol;
import automata._symboltable.AutomataSymbolTableCreator;
import automata.lang.AbstractTest;
import de.monticore.ModelingLanguage;
import de.monticore.cocos.helper.Assert;
import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.SourcePosition;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;

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
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automata/cocos/valid"));
    ModelingLanguage language = new AutomataLanguage();
    ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
    resolverConfiguration.addDefaultFilters(language.getResolvingFilters());
    Scope globalScope = new GlobalScope(modelPath, language, resolverConfiguration);
    
    Optional<AutomatonSymbol> automatonSymbol = globalScope.resolve("A", AutomatonSymbol.KIND);
    assertTrue(automatonSymbol.isPresent());
    ASTAutomaton automaton = (ASTAutomaton) automatonSymbol.get().getAstNode().get();
    
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    checker.checkAll(automaton);
    
    assertTrue(Log.getFindings().isEmpty());
  }
  
  @Test
  public void testNotExistingTransitionSource() throws IOException {
    ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/automata/cocos/invalid"));
    AutomataLanguage language = new AutomataLanguage();
    ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
    resolverConfiguration.addDefaultFilters(language.getResolvingFilters());
    GlobalScope globalScope = new GlobalScope(modelPath, language, resolverConfiguration);
    
    ASTAutomaton ast = parseModel("src/test/resources/automata/cocos/invalid/NotExistingTransitionSource.aut");
    
    Optional<AutomataSymbolTableCreator> stc = language.getSymbolTableCreator(
        resolverConfiguration, globalScope);
    stc.get().createFromAST(ast);
    
    ASTTransition transition = ast.getTransitionList().get(0);
    
    TransitionSourceExists coco = new TransitionSourceExists();
    coco.check(transition);
    
    Collection<Finding> expectedErrors = Arrays.asList(
        Finding.error("0xAUT03 The source state of the transition does not exist.",
            new SourcePosition(23, 2))
        );
    
    Assert.assertErrors(expectedErrors, Log.getFindings());
  }
  
}
