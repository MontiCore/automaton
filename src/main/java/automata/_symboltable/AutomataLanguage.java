/* (c) https://github.com/MontiCore/monticore */
package automata._symboltable;

import com.google.common.collect.ImmutableSet;
import de.monticore.utils.Names;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;

public class AutomataLanguage extends AutomataLanguageTOP {
  public static final String FILE_ENDING = "aut";
  
  public AutomataLanguage() {
    super("Automaton Language", FILE_ENDING);
  }

  protected AutomataModelLoader provideModelLoader() {
    return new AutomataModelLoader(this);
  }

  @Override
  protected Set<String> calculateModelNamesForState(String name) {
    // e.g., if p.Automaton.State, return p.Automaton
    if (!Names.getQualifier(name).isEmpty()) {
      return ImmutableSet.of(Names.getQualifier(name));
    }

    return Collections.emptySet();
  }


  public String getQualifiedModelNameFromScope(AutomataArtifactScope scope) {
    String fileName = scope.getNameOpt().orElse("symbols")+"."+getSymbolFileExtension();
    return Paths.get(scope.getPackageName(), fileName).toString();
  }

}
