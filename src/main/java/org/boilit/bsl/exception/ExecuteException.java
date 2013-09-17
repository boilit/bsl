package org.boilit.bsl.exception;

import org.boilit.bsl.core.IStatement;

/**
 * @author Boilit
 * @see
 */
public class ExecuteException extends ScriptException {
    private IStatement statement;

    public ExecuteException(IStatement statement, String message) {
        super(message);
        this.statement = statement;
    }

    public ExecuteException(IStatement statement, Throwable cause) {
        super(cause);
        this.statement = statement;
    }

    public ExecuteException(IStatement statement, String message, Throwable cause) {
        super(message, cause);
        this.statement = statement;
    }

    public ScriptException toScriptException() {
        int line = this.statement.getLine();
        int column = this.statement.getColumn();
        String message = "ErrorPosition[" + line + ", " + column + "], " + this.getMessage();
        return new ScriptException(message, this);
    }
}
