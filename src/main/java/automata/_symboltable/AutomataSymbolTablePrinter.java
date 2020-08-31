/* (c) https://github.com/MontiCore/monticore */

package automata._symboltable;

import de.monticore.symboltable.serialization.JsonPrinter;

public class AutomataSymbolTablePrinter extends AutomataSymbolTablePrinterTOP {

  public AutomataSymbolTablePrinter() {
  }

  public AutomataSymbolTablePrinter(JsonPrinter printer) {
    super(printer);
  }

  @Override protected void serializeAdditionalAutomatonSymbolAttributes(AutomatonSymbol node) {
    getJsonPrinter().member("template", node.getTemplate());
  }

}
