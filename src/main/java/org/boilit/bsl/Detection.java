package org.boilit.bsl;

/**
 * @author Boilit
 * @see
 */
public final class Detection {
    // argument label names array size.
    private int argSize = 0;
    // variable label names array size.
    private int varSize = 0;
    // variable label names array max size.
    private int maxSize = 0;
    // block vernier record.
    private int varMark = 0;
    // argument indexes in variables.
    private int[] argIndexes;
    // argument label names.
    private String[] arguments;
    // variable label names.
    private String[] variables;

    public Detection() {
        this(8);
    }

    public Detection(final int capacity) {
        this.argIndexes = new int[capacity];
        this.arguments = new String[capacity];
        this.variables = new String[capacity];
    }

    public final void clear() {
        for (int i = arguments.length - 1; i >= 0; i--) {
            arguments[i] = null;
        }
        for (int i = variables.length - 1; i >= 0; i--) {
            variables[i] = null;
        }
        argSize = 0;
        varSize = 0;
        maxSize = 0;
        varMark = 0;
    }

    /**
     * occupy a new block as current. paired with revert.
     */
    public final void occupy() {
        this.varMark = this.varSize;
    }

    /**
     * revert current block. paired with occupy.
     */
    public final void revert() {
        for (int i = this.varMark; i < this.varSize; i++) {
            variables[i] = null;
        }
        this.varSize = this.varMark;
    }

    public void addArgument(final String argument) {
        this.ensureArgCapacity(argSize + 1);
        this.ensureVarCapacity(varSize + 1);
        argIndexes[argSize] = varSize;
        arguments[argSize++] = argument;
        variables[varSize++] = argument;
        this.maxSize = Math.max(varSize, maxSize);
    }

    public void addVariable(final String variable) {
        this.ensureVarCapacity(varSize + 1);
        variables[varSize++] = variable;
        this.maxSize = Math.max(varSize, maxSize);
    }

    public final int getVarIndex(final String variable) {
        final String[] variables = this.variables;
        for (int i = varSize - 1; i >= 0; i--) {
            if (variable.equals(variables[i])) {
                return i;
            }
        }
        return -1;
    }

    public final int getArgSize() {
        return argSize;
    }

    public final int getVarSize() {
        return varSize;
    }

    public final int getMaxSize() {
        return maxSize;
    }

    public final int[] getArgIndexes() {
        return argIndexes;
    }

    public final String[] getArguments() {
        return arguments;
    }

    public final String[] getVariables() {
        return variables;
    }

    private final void ensureArgCapacity(final int minCapacity) {
        if (minCapacity > arguments.length) {
            int newCapacity = ((arguments.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            int[] oldIndex = this.argIndexes;
            this.argIndexes = new int[newCapacity];
            System.arraycopy(oldIndex, 0, this.argIndexes, 0, oldIndex.length);
            String[] oldData = this.arguments;
            this.arguments = new String[newCapacity];
            System.arraycopy(oldData, 0, this.arguments, 0, oldData.length);
        }
    }

    private final void ensureVarCapacity(final int minCapacity) {
        if (minCapacity > variables.length) {
            int newCapacity = ((variables.length * 3) >> 1) + 1;
            newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
            String[] oldData = this.variables;
            this.variables = new String[newCapacity];
            System.arraycopy(oldData, 0, this.variables, 0, oldData.length);
        }
    }
}
