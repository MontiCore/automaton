/* (c) https://github.com/MontiCore/monticore */
package automata;


import automata._ast.ASTAutomaton;
import automata._cocos.AutomataCoCoChecker;
import automata._parser.AutomataParser;
import automata._symboltable.*;
import automata.cocos.AtLeastOneInitialAndFinalState;
import automata.cocos.AutomataCoCos;
import automata.cocos.StateNameStartsWithCapitalLetter;
import automata.cocos.TransitionSourceExists;
import automata.prettyprint.PrettyPrinter;
import automata.visitors.CountStates;
import de.se_rwth.commons.logging.Log;
import org.antlr.v4.runtime.RecognitionException;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Main class for the Automaton DSL tool.
 *
 */
public class AutomataTool {
  
  /**
   * Use the single argument for specifying the single input automata file.
   *
   * @param args
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      Log.error("Please specify only one single path to the input model.");
      return;
    }
    String model = args[0];
    
    // setup the deser infrastructure
    final AutomataSymbols2Json deser = new AutomataSymbols2Json();

    // parse the model and create the AST representation
    final ASTAutomaton ast = parse(model);
    Log.info(model + " parsed successfully!", AutomataTool.class.getName());
    
    // setup the symbol table
    IAutomataArtifactScope modelTopScope = createSymbolTable(ast);

    // can be used for resolving things in the model
    Optional<StateSymbol> aSymbol = modelTopScope.resolveState("Ping");
    if (aSymbol.isPresent()) {
      Log.info("Resolved state symbol \"Ping\"; FQN = " + aSymbol.get().toString(),
          AutomataTool.class.getName());
    }
    
    // execute default context conditions
    runDefaultCoCos(ast);
    
    // execute a custom set of context conditions
    AutomataCoCoChecker customCoCos = new AutomataCoCoChecker();
    customCoCos.addCoCo(new StateNameStartsWithCapitalLetter());
    customCoCos.checkAll(ast);
    
    // store artifact scope
    String qualifiedModelName = model.replace("src/main/resources", "");
    qualifiedModelName = qualifiedModelName.replace("src/test/resources", "");
    String outputFileName = Paths.get(model).getFileName()+"sym";
    String packagePath = Paths.get(qualifiedModelName).getParent().toString();
    String storagePath = Paths.get("target/symbols", packagePath,
        outputFileName).toString();
    deser.store(modelTopScope,storagePath);
    
    // analyze the model with a visitor
    CountStates cs = new CountStates();
    cs.handle(ast);
    Log.info("The model contains " + cs.getCount() + " states.", AutomataTool.class.getName());
    
    // execute a pretty printer
    PrettyPrinter pp = new PrettyPrinter();
    pp.handle(ast);
    Log.info("Pretty printing the parsed automata into console:", AutomataTool.class.getName());
    System.out.println(pp.getResult());
  }
  
  /**
   * Parse the model contained in the specified file.
   *
   * @param model - file to parse
   * @return
   */
  public static ASTAutomaton parse(String model) {
    try {
      AutomataParser parser = new AutomataParser() ;
      Optional<ASTAutomaton> optAutomaton = parser.parse(model);
      
      if (!parser.hasErrors() && optAutomaton.isPresent()) {
        return optAutomaton.get();
      }
      Log.error("Model could not be parsed.");
    }
    catch (RecognitionException | IOException e) {
      Log.error("Failed to parse " + model, e);
    }
    return null;
  }
  
  /**
   * Create the symbol table from the parsed AST.
   *
   * @param ast
   * @return
   */
  public static IAutomataArtifactScope createSymbolTable(ASTAutomaton ast) {
    IAutomataGlobalScope gs = AutomataMill.globalScope();
    gs.clear();
    gs.setFileExt("aut");
    AutomataSymbolTableCreator symTabCreator = AutomataMill.automataSymbolTableCreator();

    IAutomataArtifactScope scope = symTabCreator.createFromAST(ast);
    gs.addSubScope(scope);
    return scope;
  }
  
  /**
   * Run the default context conditions {@link AtLeastOneInitialAndFinalState},
   * {@link TransitionSourceExists}, and
   * {@link StateNameStartsWithCapitalLetter}.
   *
   * @param ast
   */
  public static void runDefaultCoCos(ASTAutomaton ast) {
    new AutomataCoCos().getCheckerForAllCoCos().checkAll(ast);
  }
  
}
