package automata;

/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */

import java.io.IOException;
import java.util.Optional;

import automata._symboltable.AutomataSymbolTableCreator;
import automata.cocos.AutomataCoCos;
import de.monticore.symboltable.*;
import org.antlr.v4.runtime.RecognitionException;

import automata._ast.ASTAutomaton;
import automata._cocos.AutomataCoCoChecker;
import automata._parser.AutomataParser;
import automata._symboltable.AutomataLanguage;
import automata._symboltable.StateSymbol;
import automata.cocos.AtLeastOneInitialAndFinalState;
import automata.cocos.StateNameStartsWithCapitalLetter;
import automata.cocos.TransitionSourceExists;
import automata.prettyprint.PrettyPrinter;
import automata.visitors.CountStates;
import de.monticore.io.paths.ModelPath;
import de.se_rwth.commons.logging.Log;

/**
 * Main class for the automata DSL tool.
 *
 * @author (last commit) $Author$
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
    
    // setup the language infrastructure
    final AutomataLanguage lang = new AutomataLanguage();
    
    // parse the model and create the AST representation
    final ASTAutomaton ast = parse(model);
    Log.info(model + " parsed successfully!", AutomataTool.class.getName());
    
    // setup the symbol table
    ArtifactScope modelTopScope = createSymbolTable(lang, ast);

    if (lang.getSymbolTableDeserializer().isPresent()) {
      lang.getSymbolTableDeserializer().get().store(modelTopScope);
    }
    // can be used for resolving things in the model
    Optional<Symbol> aSymbol = modelTopScope.resolve("Ping", StateSymbol.KIND);
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
   * @param lang
   * @param ast
   * @return
   */
  public static ArtifactScope createSymbolTable(AutomataLanguage lang, ASTAutomaton ast) {
    final ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
    resolverConfiguration.addDefaultFilters(lang.getResolvingFilters());
    
    GlobalScope globalScope = new GlobalScope(new ModelPath(), lang, resolverConfiguration);
    
    Optional<AutomataSymbolTableCreator> symbolTable = lang.getSymbolTableCreator(
        resolverConfiguration, globalScope);
    return symbolTable.get().createFromAST(ast);
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
