package org.boilit.bsl.exception;

import org.boilit.bsl.core.IStatement;

/**
 * @author Boilit
 * @see
 */
public class ScriptException extends Exception {

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException toScriptException() {
        return this;
    }
}
