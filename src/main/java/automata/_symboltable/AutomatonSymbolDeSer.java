/* (c) https://github.com/MontiCore/monticore */

package automata._symboltable;

import de.monticore.symboltable.serialization.json.JsonObject;

public class AutomatonSymbolDeSer extends AutomatonSymbolDeSerTOP {

  @Override protected void deserializeAdditionalAutomatonSymbolAttributes(AutomatonSymbol symbol,
      JsonObject symbolJson, IAutomataScope enclosingScope) {
    String template = symbolJson.getStringMember("template");
    symbol.setTemplate(template);
  }
}
