package org.boilit.bsl.exception;

import org.boilit.bsl.core.IStatement;

/**
 * @author Boilit
 * @see
 */
public class ParseException extends ScriptException {
    private IStatement statement;

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(IStatement statement, String message) {
        super(message);
        this.statement = statement;
    }

    public ParseException(IStatement statement, Throwable cause) {
        super(cause);
        this.statement = statement;
    }

    public ParseException(IStatement statement, String message, Throwable cause) {
        super(message, cause);
        this.statement = statement;
    }

    public ScriptException toScriptException() {
        if(this.statement == null) {
            return this;
        } else {
            int line = this.statement.getLine();
            int column = this.statement.getColumn();
            String message = "ErrorPosition[" + line + ", " + column + "], " + this.getMessage();
            return new ScriptException(message, this);
        }
    }
}
