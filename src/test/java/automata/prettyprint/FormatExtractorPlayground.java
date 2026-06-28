/* (c) https://github.com/MontiCore/monticore */
package automata.prettyprint;

import automata.AutomataMill;
import automata._parser.AutomataAntlrLexer;
import automata._parser.AutomataAntlrParser;
import automata._parser.AutomataAntlrParserBaseVisitor;
import automata._prettyprint.AutomataFullPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import de.se_rwth.commons.logging.LogStub;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.jspecify.annotations.NonNull;
import smile.data.DataFrame;
import smile.data.formula.Formula;
import smile.regression.RandomForest;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FormatExtractorPlayground {
  
  static double[][] merge(double[][] x, double[][] y, int index) {
    double[][] z = new double[x.length][];
    
    for (int i = 0; i < x.length; i++) {
      z[i] = new double[x[i].length + 1];
      
      // Copy the original row
      System.arraycopy(x[i], 0, z[i], 0, x[i].length);
      
      // Append the label
      z[i][x[i].length] = y[i][index];
    }
    return z;
  }
  
  static int[][] merge(int[][] x, int[][] y, int index) {
    int[][] z = new int[x.length][];
    
    for (int i = 0; i < x.length; i++) {
      z[i] = new int[x[i].length + 1];
      
      // Copy the original row
      System.arraycopy(x[i], 0, z[i], 0, x[i].length);
      
      // Append the label
      z[i][x[i].length] = y[i][index];
    }
    return z;
  }
  
  public static void main(String[] args) throws Exception {
    LogStub.initPlusLog();
    
    List<SerializedInfo> data = new ArrayList<>();
    // Extract format from existing files
    //    data.addAll(extractFormat("src/test/resources/automata/prettyprinter/valid/A.aut"));
    for (int i = 0; i < 5; i++) // stochastics are fun, ensure enough data is present
    {
      data.addAll(extractFormat("src/test/resources/automata/prettyprinter/valid/B.aut"));
    }
    //    data.addAll(extractFormat("src/test/resources/automata/prettyprinter/valid/C.aut"));
    
    // convert data to vectors
    int[][] x = new int[data.size()][];
    int[][] y = new int[data.size()][];
    for (int i = 0; i < data.size(); i++) {
      x[i] = data.get(i).toX();
      y[i] = data.get(i).toY();
    }
    
    // output input data
    System.err.println("#########################");
    for (int[] ddd : merge(merge(merge(merge(x, y, 0), y, 1), y, 2), y, 3)) {
      System.err.println(Arrays.toString(ddd));
    }
    System.err.println("#########################");
    
    // Fit RFs
    RandomForest[] rfs = new RandomForest[4];
    for (int output = 0; output < 4; output++) {
      DataFrame input =
          DataFrame.of(merge(x, y, output), "type", "length", "production", "position", "last",
              "next", "output");
      rfs[output] = RandomForest.fit(Formula.lhs("output"), input);
    }
    
    ///
    var _SYMBOLIC_NAMES =
        Arrays.asList(null, "LTLT", "GTGT", "AUTOMATON1673671408", "INITIAL1948342084",
            "FINAL97436022", "LCURLY", "STATE109757585", "SEMI", "RCURLY", "MINUS", "GT",
            "ML_COMMENT", "SL_COMMENT", "WS", "Name"); // from AutomataAntlrParser
    var Rulenames = Arrays.asList(AutomataAntlrParser.ruleNames);
    // Use the new FormattingPrinter
    IndentPrinter printer =
        new FormattingPrinter(new RFFormatter(_SYMBOLIC_NAMES, Rulenames, rfs, data));
    
    // Replace the Automata PP of the AutomataFullPrettyPrinter
    AutomataFullPrettyPrinter pp = new AutomataFullPrettyPrinter(printer);
    // with an Automata PP emitting tokens
    var myPP = new MyAutPrettyPrinter(pp.getPrinter(), false);
    pp.getTraverser().setAutomataHandler(myPP);
    pp.getTraverser().add4Automata(myPP);
    
    // And test a model
    var astOpt =
        AutomataMill.parser().parse("src/test/resources/automata/prettyprinter/valid/C.aut");
    
    String printed = pp.prettyprint(astOpt.get());
    System.err.println();
    System.err.println(printed);
  }
  
  private static @NonNull List<SerializedInfo> extractFormat(String file) throws IOException {
    AutomataAntlrLexer lexer =
        new AutomataAntlrLexer(org.antlr.v4.runtime.CharStreams.fromFileName(file));
    org.antlr.v4.runtime.CommonTokenStream tokens =
        new org.antlr.v4.runtime.CommonTokenStream(lexer);
    AutomataAntlrParser parser = new AutomataAntlrParser(tokens);
    lexer.setMCParser(parser);
    lexer.removeErrorListeners();
    lexer.addErrorListener(new de.monticore.antlr4.MCErrorListener(parser));
    parser.setFilename(file);
    
    var ast = parser.automaton();
    
    Stack<String> productionNames = new Stack<>();
    List<TokenInfo> tokenInfos = new ArrayList<>();
    
    List<String> rulenames = Arrays.asList(AutomataAntlrParser.ruleNames);
    
    var pp = new AutomataAntlrParserBaseVisitor<>() {
      
      @Override
      public Object visitTerminal(TerminalNode node) {
        System.err.println(">>>visitTerminal" + node.getText());
        int line = node.getSymbol().getLine();
        int col = node.getSymbol().getCharPositionInLine();
        // TODO: add usage-name at some point
        
        tokenInfos.add(new TokenInfo(line, col, // positions
            productionNames.peek(), // productionName
            rulenames.indexOf(productionNames.peek().toLowerCase()), //
            node.getText(), //
            AutomataAntlrParser.VOCABULARY.getSymbolicName(node.getSymbol().getType()), //
            node.getSymbol().getType(), //
            node.getSymbol().getStartIndex(), //
            node.getSymbol().getStopIndex() //
        ));
        return super.visitTerminal(node);
      }
      
      @Override
      public Object visitChildren(RuleNode node) {
        String ruleName = node.getClass().getSimpleName();
        ruleName = ruleName.substring(0, ruleName.length() - "Context".length());
        productionNames.push(ruleName);
        
        Object ret = super.visitChildren(node);
        
        productionNames.pop();
        return ret;
      }
    };
    
    ast.accept(pp);
    
    TokenInfo last = null, next = null, current;
    List<SerializedInfo> data = new ArrayList<>();
    Stack<Integer> indentLevel = new Stack<>();
    indentLevel.push(0);
    for (int i = 0; i < tokenInfos.size(); i++) {
      current = tokenInfos.get(i);
      if (i < tokenInfos.size() - 1) {
        next = tokenInfos.get(i + 1);
      }
      else {
        next = null;
      }
      
      boolean isLineBreakPre = last == null ? false : last.line < current.line;
      boolean isLineBreakPost = next == null ? false : current.line < next.line;
      boolean spaceFollowing = next == null ? true : current.stopIndex() + 1 != next.startIndex();
      
      //  { -> post
      //  } -> pre
      
      short indent = 0;
      System.err.println(current);
      if (isLineBreakPost) { //  { -> post
        int currentIndent = indentLevel.peek();
        int nextIndent = next.col;
        System.err.println("next " + current.line + "/" + next.line);
        System.err.println("nextIndent > currentIndent" + nextIndent + ">" + currentIndent);
        if (nextIndent > currentIndent) {
          indent = 1;
          indentLevel.push(nextIndent);
          System.err.println("++indent");
        }
      }
      if (isLineBreakPre) { //  } -> pre
        int lastIndent = indentLevel.peek();
        int currentIndent = current.col;
        System.err.println("currentIndent < lastIndent" + currentIndent + "<" + lastIndent);
        if (currentIndent < lastIndent) {
          indent = -1;
          indentLevel.push(currentIndent);
          System.err.println("--unindent");
        }
      }
      
      data.add(new SerializedInfo(current.text(), //
          current.symbolicName(), //
          current.tokenType(), //
          "todo", // Position/usagename NYI
          current.production, current.productionInt,//
          last == null ? "" : last.symbolicName(),//
          last == null ? -1 : last.tokenType(),//
          last == null ? "" : last.text(),//
          next == null ? "" : next.symbolicName(),//
          next == null ? -1 : next.tokenType(),//
          next == null ? "" : next.text(),//
          42, // TODO: depth
          isLineBreakPre, //
          isLineBreakPost, //
          spaceFollowing, //
          indent//
      ));
      
      last = current;
    }
    return data;
  }
  
  record TokenInfo(int line, int col, String production, int productionInt, String text,
      String symbolicName, int tokenType, int startIndex, int stopIndex) {}
  
  record SerializedInfo(String token, String tokenType, int tokenTypeInt, String position,
      String productionName, int productionInt, String lastTokenType, int lastTokenTypeInt,
      String lastTokenString, String nextTokenType, int nextTokenTypeInt, String nextTokenString,
      int depth, boolean isLineBreakPre, boolean isLineBreakPost, boolean spaceFollowing,
      short indent // -1,0,+1
  
  ) {
    
    int[] toX() {
      return new int[] { tokenTypeInt, //token type
          token == null ? 0 : token.length(), // length
          productionInt, // production
          -1, // position(todo)
          lastTokenTypeInt, // last
          nextTokenTypeInt, // next
          // outputs
        
      };
    }
    
    int[] toY() {
      return new int[] { isLineBreakPre ? 1 : 0, // isLineBreakPre
          isLineBreakPost ? 1 : 0,  // isLineBreakPost
          spaceFollowing ? 1 : 0, // spaceFollowing
          indent  // indent
      };
    }
    
    double[] toVector() {
      return new double[] { tokenTypeInt, //token type
          token == null ? 0 : token.length(), // length
          productionInt, // production
          -1, // position(todo)
          lastTokenTypeInt, // last
          nextTokenTypeInt, // next
          // outputs
          isLineBreakPre ? 0 : 1, // isLineBreakPre
          isLineBreakPost ? 0 : 1,  // isLineBreakPost
          spaceFollowing ? 0 : 1, // spaceFollowing
          indent  // indent
      };
    }
    
  }
  
  /**
   * IFormatter using random forest to check what to do
   */
  static class RFFormatter implements IFormatter {
    
    final List<String> _SYMBOLIC_NAMES;
    final List<String> Rulenames;
    final RandomForest[] rfs;
    
    // for debugging
    final AtomicInteger counter = new AtomicInteger(0);
    final List<SerializedInfo> data;
    
    public RFFormatter(List<String> _SYMBOLIC_NAMES, List<String> rulenames, RandomForest[] rfs,
        List<SerializedInfo> data) {
      this._SYMBOLIC_NAMES = _SYMBOLIC_NAMES;
      Rulenames = rulenames;
      this.rfs = rfs;
      this.data = data;
    }
    
    @Override
    public int getFormatOptions(String token, String tokenType, String position,
        String productionName, String lastTokenType, String lastTokenString, String nextTokenType,
        String nextTokenString, int depth) {
      // token=automaton, tokenType=AUTOMATON, position=0, lastTokenType=null, lastTokenString=null, nextTokenType=Name, nextTokenString=A
      // token=A, tokenType=Name, position=name, lastTokenType=AUTOMATON, lastTokenString=automaton, nextTokenType=LCUR, nextTokenString={
      
      int[] inputData = { _SYMBOLIC_NAMES.indexOf(tokenType), // type
          token.length(), // length
          Rulenames.indexOf(productionName.toLowerCase()), // production
          -1, // position: NYI
          _SYMBOLIC_NAMES.indexOf(lastTokenType), // lastType
          _SYMBOLIC_NAMES.indexOf(nextTokenType) // nextType
      };
      DataFrame input =
          DataFrame.of(new int[][] { inputData }, "type", "length", "production", "position",
              "last", "next");
      int ret = 0;
      int tokenCounter = counter.getAndIncrement();
      System.err.println("III= " + token);
      System.err.println("i=" + Arrays.toString(inputData));
      System.err.println("x=" + Arrays.toString(data.get(tokenCounter).toX()));
      System.err.println("y=" + Arrays.toString(data.get(tokenCounter).toY()));
      
      System.err.println(
          "==> " + token + " pre=" + rfs[0].predict(input)[0] + " post=" + rfs[1].predict(input)[0]
              + " space=" + rfs[2].predict(input)[0] + " indent=" + rfs[3].predict(input)[0]);
      if (rfs[0].predict(input)[0] > .5) {
        ret |= IFormatter.LINEBREAK_PRE;
      }
      if (rfs[1].predict(input)[0] > .5) {
        ret |= IFormatter.LINEBREAK_POST;
      }
      if (rfs[2].predict(input)[0] > .5) {
        ret |= IFormatter.SPACE_FOLLOWING;
      }
      double indentPred = rfs[3].predict(input)[0];
      if (indentPred > .5) {
        ret |= IFormatter.INDENT;
      }
      else if (indentPred < -.5) {
        ret |= IFormatter.UNINDENT;
      }
      
      return ret;
    }
  }
  
}
