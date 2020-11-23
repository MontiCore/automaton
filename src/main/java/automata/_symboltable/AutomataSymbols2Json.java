/* (c) https://github.com/MontiCore/monticore */

package automata._symboltable;

import de.monticore.symboltable.serialization.JsonPrinter;

public class AutomataSymbols2Json extends AutomataSymbols2JsonTOP {

  public AutomataSymbols2Json() {
  }

  public AutomataSymbols2Json(JsonPrinter printer) {
    super(printer);
  }

  @Override protected void serializeAdditionalAutomatonSymbolAttributes(AutomatonSymbol node) {
    getJsonPrinter().member("template", node.getTemplate());
  }

}
