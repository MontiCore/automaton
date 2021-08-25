/* (c) https://github.com/MontiCore/monticore */
package automata.prettyprint;

import automata.AutomataMill;
import automata._ast.ASTAutomaton;
import automata.lang.AbstractTest;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link PrettyPrinter}.
 */
public class PrettyPrinterTest extends AbstractTest {
  
  @BeforeClass
  public static void init() {
    Log.enableFailQuick(false);
    AutomataMill.init();
  }

  @Test
  public void test() {
    ASTAutomaton automaton = parseModel("src/test/resources/automata/prettyprinter/valid/A.aut");
    PrettyPrinter pp = new PrettyPrinter();
    pp.handle(automaton);

    String actual = pp.getResult();
    String expected = "automaton A {\n  state S <<initial>> <<final>>;\n}\n";
    assertEquals(expected, actual);
  }

}
