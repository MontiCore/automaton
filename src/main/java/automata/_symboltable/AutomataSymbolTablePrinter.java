/* (c) https://github.com/MontiCore/monticore */

package automata._symboltable;

public class AutomataSymbolTablePrinter extends AutomataSymbolTablePrinterTOP {

  @Override protected void serializeAdditionalAutomatonSymbolAttributes(AutomatonSymbol node) {
    getJsonPrinter().member("template", node.getTemplate());
  }

}
