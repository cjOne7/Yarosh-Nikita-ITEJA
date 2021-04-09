package parser.statements;

import parser.statements.loops.*;
import parser.statements.blocks.*;
import parser.statements.functions.FunctionStatement;

/**
 * Interface with only one method <tt>execute</tt> provides generalization and implementation for all statements
 * in programming language
 *
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
     * Execute statement
     */
    void execute();
}
