import AST.RootNode;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import Parser.MxStarLexer;
import Parser.MxStarParser;
import Util.MxErrorListener;
import Util.globalScope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) throws Exception {
        InputStream input = System.in;
//        String name = "testcases/sema/basic-package/basic-11.mx";
//        InputStream input = new FileInputStream(name);
        try {
            MxStarLexer lexer = new MxStarLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxStarParser parser = new MxStarParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();

            ASTBuilder test = new ASTBuilder();
            RootNode root = (RootNode) test.visit(parseTreeRoot);
            globalScope glbScope = new globalScope(null);
            SymbolCollector symbolCollector = new SymbolCollector();
            symbolCollector.visit(root);
            glbScope=symbolCollector.glbScope;
            SemanticChecker semanticCheck = new SemanticChecker(glbScope);
            semanticCheck.visit(root);
        } catch (RuntimeException ER) {
            System.err.println(ER.getMessage());
            throw new RuntimeException();
        }
    }
}
