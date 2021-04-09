package parser.statements;

import parser.statements.loops.*;
import parser.statements.blocks.*;
import parser.statements.functions.FunctionStatement;

/**
 * @see BlockStatement
 * @see StatementBlock
 * @see FunctionStatement
 * @see BreakStatement
 * @see ForStatement
 * @see WhileStatement
 * @see RepeatStatement
 * @see AssignmentStatement
 * @see ExitStatement
 * @see IfStatement
 * @see ReadStatement
 */
@FunctionalInterface
public interface IStatement {
    /**
     *
     */
    void execute();
}
