/* (c) https://github.com/MontiCore/monticore */
package automata.prettyprint;

import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata._prettyprint.AutomataFullPrettyPrinter;
import automata.lang.AbstractTest;
import de.monticore.prettyprint.IndentPrinter;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutomataPrettyPrinterTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }

  @Test
  public void testPrettyPrinter() {
    ASTAutomaton automaton = parseModel("src/test/resources/automata/prettyprinter/valid/A.aut");
    AutomataFullPrettyPrinter pp = new AutomataFullPrettyPrinter(new IndentPrinter());
    String printed = pp.prettyprint(automaton);
    ASTAutomaton reparsed = parseStringModel(printed);
    String reprinted = pp.prettyprint(reparsed);
    assertEquals(printed, reprinted);
  }

}
