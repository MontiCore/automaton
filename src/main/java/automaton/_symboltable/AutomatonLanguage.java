/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automaton._symboltable;

public class AutomatonLanguage extends AutomatonLanguageTOP {
  public static final String FILE_ENDING = "aut";
  
  public AutomatonLanguage() {
    super("Automaton Language", FILE_ENDING);

    setModelNameCalculator(new AutomatonModelNameCalculator());
  }
  

  @Override
  protected AutomatonModelLoader provideModelLoader() {
    return new AutomatonModelLoader(this);
  }
}
