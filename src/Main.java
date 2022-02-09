import AST.RootNode;
import Backend.ASMPrinter;
import Backend.IRBuilder;
import Backend.IRPrinter;
import Backend.InstSelect;
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
//        InputStream input = System.in;
        String name = "F:\\Programming\\MxCompiler\\test.mx";
        InputStream input = new FileInputStream(name);
//        try {
            MxStarLexer lexer = new MxStarLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxStarParser parser = new MxStarParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();

            ASTBuilder test = new ASTBuilder();
            RootNode root = (RootNode) test.visit(parseTreeRoot);
            globalScope glbScope;
            SymbolCollector symbolCollector = new SymbolCollector();
            symbolCollector.visit(root);
            glbScope=symbolCollector.glbScope;
            SemanticChecker semanticCheck = new SemanticChecker(glbScope);
            semanticCheck.visit(root);

            IRBuilder irBuilder = new IRBuilder(glbScope);
            root.accept(irBuilder);
            IRPrinter llvm_naive = new IRPrinter("naive_llvm.ll",name);
            llvm_naive.init(irBuilder);

            InstSelect instSelect = new InstSelect(irBuilder);
            ASMPrinter RISCV_out = new ASMPrinter("RISCV_out.s");
            RISCV_out.init(instSelect);
//        } catch (RuntimeException ER) {
//            System.err.println(ER.getMessage());
//            throw new RuntimeException();
//        }
    }
}
