import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LoadCSV {
    public static void main(String[] args) {
        String s = "Details,Month,Amount\n" +
                "Mid Bonus,June,\"$2,000\"\n";
        CodePointBuffer buffer = CodePointBuffer.withChars(CharBuffer.wrap(s.toCharArray()));
        CSVLexer lexer = new CSVLexer(CodePointCharStream.fromBuffer(buffer));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        CSVParser parser = new CSVParser(commonTokenStream);
        ParseTree tree = parser.file();
        ParseTreeWalker walker = new ParseTreeWalker();
        Loader loader = new Loader();
        walker.walk(loader, tree);
        System.out.println(loader.rows);
    }
    
    
    public static class Loader extends CSVBaseListener {
        public static final String EMPTY = "";
        List<Map<String,String>> rows = new ArrayList<>();
        List<String> header;
        List<String> currentRowFieldValues;
    
        public void exitString(CSVParser.StringContext ctx) {
            currentRowFieldValues.add(ctx.STRING().getText());
        }
        
        public void exitText(CSVParser.TextContext ctx) {
            currentRowFieldValues.add(ctx.TEXT().getText());
        }
        
        public void exitEmpty(CSVParser.EmptyContext ctx) {
            currentRowFieldValues.add(EMPTY);
        }
        
        public void exitHdr(CSVParser.HdrContext ctx) {
            header = new ArrayList<>();
            header.addAll(currentRowFieldValues);
        }
        
        public void enterRow(CSVParser.RowContext ctx) {
            currentRowFieldValues = new ArrayList<>();
        }
        
        public void exitRow(CSVParser.RowContext ctx) {
            if (ctx.getParent().getRuleIndex() == CSVParser.RULE_hdr) return;
            Map<String,String> m = new LinkedHashMap<>();
            int i = 0;
            for (String v : currentRowFieldValues) {
                m.put(header.get(i),v);
                i++;
            }
            rows.add(m);
        }
    }
    
    
}
