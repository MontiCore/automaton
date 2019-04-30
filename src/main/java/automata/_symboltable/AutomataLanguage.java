/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/
 */
package automata._symboltable;

public class AutomataLanguage extends AutomataLanguageTOP {
  public static final String FILE_ENDING = "aut";
  
  public AutomataLanguage() {
    super("Automata Language", FILE_ENDING);

    setModelNameCalculator(new AutomataModelNameCalculator());
  }
  

  @Override
  protected AutomataModelLoader provideModelLoader() {
    return new AutomataModelLoader(this);
  }
}
