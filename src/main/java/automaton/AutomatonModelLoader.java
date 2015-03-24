/*
 * Copyright (c) 2015 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton;

import automaton._ast.ASTAutomaton;
import automaton.symboltable.AutomatonSymbolTableCreator;
import de.monticore.modelloader.ModelingLanguageModelLoader;
import de.monticore.symboltable.ArtifactScope;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolverConfiguration;
import de.monticore.symboltable.Scope;
import de.se_rwth.commons.logging.Log;

public class AutomatonModelLoader extends ModelingLanguageModelLoader<ASTAutomaton> {
  
  public AutomatonModelLoader(AutomatonLanguage language) {
    super(language);
  }
  
  @Override
  protected void createSymbolTableFromAST(final ASTAutomaton ast, final String modelName,
      final MutableScope enclosingScope, final ResolverConfiguration resolverConfiguration) {
    final AutomatonSymbolTableCreator symbolTableCreator = getModelingLanguage()
        .getSymbolTableCreator(resolverConfiguration, enclosingScope).orNull();
    
    if (symbolTableCreator != null) {
      Log.info("Start creation of symbol table for model \"" + modelName + "\".",
          AutomatonModelLoader.class
              .getSimpleName());
      final Scope scope = symbolTableCreator.createFromAST(ast);
      
      if (!(scope instanceof ArtifactScope)) {
        Log.warn("Top scope of model " + modelName + " is expected to be a compilation scope, but"
            + " is scope \"" + scope.getName() + "\"");
      }
      
      Log.info("Created symbol table for model \"" + modelName + "\".", AutomatonModelLoader.class
          .getSimpleName());
    }
    else {
      Log.warn("No symbol created, because '" + getModelingLanguage().getName()
          + "' does not define a symbol table creator.");
    }
  }
  
  @Override
  public AutomatonLanguage getModelingLanguage() {
    return (AutomatonLanguage) super.getModelingLanguage();
  }
}
