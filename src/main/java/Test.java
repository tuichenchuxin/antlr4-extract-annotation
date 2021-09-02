import org.antlr.v4.runtime.CodePointBuffer;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import java.nio.CharBuffer;

public class Test {
    public static void main(String[] args) {
        String s = "-- s\n" +
                "{1,2} # 123\n" +
                "-- end";
        CodePointBuffer buffer = CodePointBuffer.withChars(CharBuffer.wrap(s.toCharArray()));
        ArrayParserLexer lexer = new ArrayParserLexer(CodePointCharStream.fromBuffer(buffer));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        ArrayParserParser parser = new ArrayParserParser(commonTokenStream);
        ParseTree tree = parser.init();
        commonTokenStream.getTokens().stream().filter(each -> each.getChannel() == Token.HIDDEN_CHANNEL).forEach(each -> System.out.println(each.getText()));
    }
}
