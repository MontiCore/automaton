/* (c) https://github.com/MontiCore/monticore */
package automata.cocos;

import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata._cocos.AutomataCoCoChecker;
import automata._symboltable.IAutomataArtifactScope;
import automata.lang.AbstractTest;
import de.monticore.runtime.junit.MCAssertions;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AutomataCoCosTest extends AbstractTest {

  static Stream<Arguments> testArgs() {
    return Stream.of(
        Arguments.argumentSet(
            "StateNameStartsWithCapitalLetter",
            "src/test/resources/automata/cocos/invalid/StateDoesNotStartWithCapitalLetter.aut",
            "0xB4002"),
        Arguments.argumentSet(
            "AtLeastOneInitialState",
            "src/test/resources/automata/cocos/invalid/MissingInitialState.aut",
            "0xB4114"),
        Arguments.argumentSet(
            "AtLeastOneFinalState",
            "src/test/resources/automata/cocos/invalid/MissingFinalState.aut",
            "0xB4115"),
        Arguments.argumentSet(
            "TransitionStatesExist",
            "src/test/resources/automata/cocos/invalid/NotExistingTransitionSource.aut",
            "0xB4003"),
        Arguments.argumentSet(
            "AutomatonIsDeterministic",
            "src/test/resources/automata/cocos/invalid/NonDeterministicAutomaton.aut",
            "0x14708")
    );
  }
  
  @BeforeEach
  public void setUp() throws RecognitionException {
    AutomataMill.reset();
    AutomataMill.init();
    LogStub.init();
    Log.enableFailQuick(false);
    Log.getFindings().clear();
  }
  
  @Test
  public void testAllValid(){
    ASTAutomaton ast = parseModel("src/test/resources/automata/cocos/valid/A.aut");
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    IAutomataArtifactScope artifactScope = AutomataMill.scopesGenitorDelegator().createFromAST(ast);

    checker.checkAll(ast);
    MCAssertions.assertNoFindings();
  }
  
  @ParameterizedTest
  @MethodSource("testArgs")
  public void testAllInvalid(String model, String errorCode) {
    ASTAutomaton ast = parseModel(model);
    AutomataCoCoChecker checker = new AutomataCoCos().getCheckerForAllCoCos();
    AutomataMill.scopesGenitorDelegator().createFromAST(ast);

    checker.checkAll(ast);
    MCAssertions.assertHasFindingStartingWith(errorCode);
  }

}
