/* (c) https://github.com/MontiCore/monticore */
package automata;

import automata._ast.ASTAutomaton;
import automata._cocos.AutomataCoCoChecker;
import automata._symboltable.AutomataScopesGenitor;
import automata._symboltable.IAutomataArtifactScope;
import automata._symboltable.IAutomataGlobalScope;
import automata._symboltable.StateSymbol;
import automata._visitor.AutomataTraverser;
import automata.cocos.AtLeastOneInitialAndFinalState;
import automata.cocos.AutomataCoCos;
import automata.cocos.StateNameStartsWithCapitalLetter;
import automata.cocos.TransitionStatesExist;
import automata.prettyprint.PrettyPrinter;
import automata.visitors.CountStates;
import automata2cd.Automata2CDConverter;
import com.google.common.collect.Lists;
import de.monticore.cd.codegen.CDGenerator;
import de.monticore.cd.codegen.CdUtilsPrinter;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.TemplateController;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.monticore.io.paths.MCPath;
import de.se_rwth.commons.logging.Log;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static de.se_rwth.commons.Names.getPathFromPackage;

public class AutomataTool extends AutomataToolTOP {
  
  @Override
  public void run(String[] args) {
    initOptions();
    if (args.length != 1) {
      Log.error("Please specify only one single path to the input model.");
      return;
    }
    String model = args[0];
    
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
    String symFile = "target/symbols/" + getPathFromPackage(modelTopScope.getFullName()) + ".autsym";
    storeSymbols(modelTopScope, symFile);
    
    // analyze the model with a visitor
    CountStates cs = new CountStates();
    AutomataTraverser t = AutomataMill.traverser();
    t.add4Automata(cs);
    ast.accept(t);
    Log.info("The model contains " + cs.getCount() + " states.", AutomataTool.class.getName());
    
    // execute a pretty printer
    prettyPrint(ast, null);
    
    generateCD(ast);
  }
  
  /**
   * Create the symbol table from the parsed AST.
   *
   * @param node Input AST.
   * @return The symbol table created from the AST.
   */
  @Override
  public IAutomataArtifactScope createSymbolTable(ASTAutomaton node) {
    IAutomataGlobalScope gs = AutomataMill.globalScope();
    gs.clear();
    
    AutomataScopesGenitor genitor = AutomataMill.scopesGenitor();
    AutomataTraverser traverser = AutomataMill.traverser();
    traverser.setAutomataHandler(genitor);
    traverser.add4Automata(genitor);
    genitor.putOnStack(gs);
    
    IAutomataArtifactScope scope = genitor.createFromAST(node);
    gs.addSubScope(scope);
    return scope;
  }
  
  @Override
  public void prettyPrint(ASTAutomaton ast, String file) {
    PrettyPrinter pp = new PrettyPrinter();
    pp.handle(ast);
    Log.info("Pretty printing the parsed automata into console:", AutomataTool.class.getName());
    System.out.println(pp.getResult());
  }
  
  
  /**
   * Run the default context conditions {@link AtLeastOneInitialAndFinalState},
   * {@link TransitionStatesExist}, and
   * {@link StateNameStartsWithCapitalLetter}.
   *
   * @param ast The input AST.
   */
  @Override
  public void runDefaultCoCos(ASTAutomaton ast) {
    new AutomataCoCos().getCheckerForAllCoCos().checkAll(ast);
  }
  
  public void generateCD(ASTAutomaton astAutomaton) {
    
    GeneratorSetup setup = new GeneratorSetup();
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    setup.setGlex(glex);
    glex.setGlobalValue("cdPrinter", new CdUtilsPrinter());
    
    String configTemplate = "automaton2cd.Automaton2CD";
    TemplateController tc = setup.getNewTemplateController(configTemplate);
    CDGenerator generator = new CDGenerator(setup);
    TemplateHookPoint hpp = new TemplateHookPoint(configTemplate);
    List<Object> configTemplateArgs;
    // select the conversion variant:
    Automata2CDConverter converter = new Automata2CDConverter();
    configTemplateArgs = Arrays.asList(glex, converter, setup.getHandcodedPath(), generator);
    
    hpp.processValue(tc, astAutomaton, configTemplateArgs);
  }
}
