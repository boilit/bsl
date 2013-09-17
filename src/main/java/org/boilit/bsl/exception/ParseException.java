package org.boilit.bsl.exception;

import org.boilit.bsl.core.IStatement;

/**
 * @author Boilit
 * @see
 */
public class ParseException extends ScriptException {
    public ParseException(String message) {
        super(message);
    }

    public ScriptException toScriptException() {
        return this;
    }
}
