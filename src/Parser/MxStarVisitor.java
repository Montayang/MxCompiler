// Generated from F:/Programming/MxCompiler/src/Parser\MxStar.g4 by ANTLR 4.9.1
package Parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxStarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxStarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#mainFn}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMainFn(MxStarParser.MainFnContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#varDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDef(MxStarParser.VarDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#classDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDef(MxStarParser.ClassDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#suite}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuite(MxStarParser.SuiteContext ctx);
	/**
	 * Visit a parse tree produced by the {@code block}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(MxStarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vardefStmt}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardefStmt(MxStarParser.VardefStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(MxStarParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(MxStarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pureExprStmt}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPureExprStmt(MxStarParser.PureExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStmt(MxStarParser.EmptyStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomExpr(MxStarParser.AtomExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxStarParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxStarParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(MxStarParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MxStarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxStarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MxStarParser.TypeContext ctx);
}