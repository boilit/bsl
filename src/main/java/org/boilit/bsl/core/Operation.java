package org.boilit.bsl.core;

import org.boilit.bsl.exception.ExecuteException;

import java.util.List;

/**
 * @author Boilit
 * @see
 */
public final class Operation {
    public static final boolean toBool(final Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue();
        } else if (o instanceof Number) {
            return ((Number) o).doubleValue() != 0;
        } else if (o instanceof String) {
            return "true".equalsIgnoreCase((String) o);
        } else {
            return true;
        }
    }

    public static final String toString(final Object object) {
        return object == null ? "" : object.toString();
    }

    public static final String merge(final Object... os) {
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0, n = os.length; i < n; i++) {
            buffer.append(Operation.toString(os[i]));
        }
        return buffer.toString();
    }

    public static final String merge(final List<Object> os) {
        final StringBuffer builder = new StringBuffer();
        for (int i = 0, n = os.size(); i < n; i++) {
            builder.append(Operation.toString(os.get(i)));
        }
        return builder.toString();
    }

    public static final boolean doLgcOr(final Object o1, final Object o2) {
        return toBool(o1) || toBool(o2);
    }

    public static final boolean doLgcAnd(final Object o1, final Object o2) {
        return toBool(o1) && toBool(o2);
    }

    public static final boolean doLgcNot(final Object o) {
        return !toBool(o);
    }

    public static final boolean doLgcTee(final Object o1, final Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1 instanceof Number && o2 instanceof Number) {
            return ((Number) o1).doubleValue() == ((Number) o2).doubleValue();
        }
        return false;
    }

    public static final boolean doLgcTne(final Object o1, final Object o2) {
        return !doLgcTee(o1, o2);
    }

    public static final boolean doLgcTle(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        return doLgcTlt(statement, o1, o2) || doLgcTee(o1, o2);
    }

    public static final boolean doLgcTge(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        return doLgcTgt(statement, o1, o2) || doLgcTee(o1, o2);
    }

    public static final boolean doLgcTlt(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            return ((Number) o1).doubleValue() < ((Number) o2).doubleValue();
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't < !");
    }

    public static final boolean doLgcTgt(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            return ((Number) o1).doubleValue() > ((Number) o2).doubleValue();
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't > !");
    }

    public static final Object doNumAdd(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return mathCast(n1, n2, n1.doubleValue() + n2.doubleValue());
        } else if (o1 instanceof String || o2 instanceof String) {
            return Operation.toString(o1) + Operation.toString(o2);
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't + !");
    }

    public static final Object doNumAdd1(final AbstractStatement statement, final Object o) throws Exception {
        if (o instanceof Number) {
            return mathCast((Number) o, new Byte((byte) 1), ((Number) o).doubleValue() + (byte) 1);
        }
        throw new ExecuteException(statement, "Object[" + o + "] was not number, can't +1 !");
    }

    public static final Object doNumSub(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return mathCast(n1, n2, n1.doubleValue() - n2.doubleValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't +1 !");
    }

    public static final Object doNumSub1(final AbstractStatement statement, final Object o) throws Exception {
        if (o instanceof Number) {
            return mathCast((Number) o, new Byte((byte) 1), ((Number) o).doubleValue() - (byte) 1);
        }
        throw new ExecuteException(statement, "Object[" + o + "] was not number, can't -1 !");
    }

    public static final Object doNumMul(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return mathCast(n1, n2, n1.doubleValue() * n2.doubleValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't * !");
    }

    public static final Object doNumDiv(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return mathCast(n1, n2, n1.doubleValue() / (n2.doubleValue() == 0 ? 1 : n2.doubleValue()));
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't / !");
    }

    public static final Object doNumPow(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return Math.pow(n1.doubleValue(), n2.doubleValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't pow !");
    }

    public static final Object doNumMod(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return mathCast(n1, n2, n1.doubleValue() % n2.doubleValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't % !");
    }

    public static final Object doNumNgt(final AbstractStatement statement, final Object o) throws Exception {
        if (o instanceof Number) {
            return ngtCast((Number) o);
        }
        throw new ExecuteException(statement, "Object[" + o + "] was not number, can't negative !");
    }

    private static final Object mathCast(final Number o1, final Number o2, final double value) {
        if (o1 instanceof Double || o2 instanceof Double) {
            return value;
        }
        if (o1 instanceof Long || o2 instanceof Long) {
            if (o1 instanceof Float || o2 instanceof Float) {
                return value;
            } else {
                return (long) value;
            }
        }
        if (o1 instanceof Float || o2 instanceof Float) {
            return (float) value;
        }
        if (o1 instanceof Integer || o2 instanceof Integer) {
            return (int) value;
        }
        if (o1 instanceof Short || o2 instanceof Short) {
            return (short) value;
        }
        if (o1 instanceof Byte || o2 instanceof Byte) {
            return (byte) value;
        }
        return value;
    }

    private static final Object ngtCast(final Number o) {
        final double value = -o.doubleValue();
        if (o instanceof Double) {
            return value;
        }
        if (o instanceof Float) {
            return (float) value;
        }
        if (o instanceof Long) {
            return (long) value;
        }
        if (o instanceof Integer) {
            return (int) value;
        }
        if (o instanceof Short) {
            return (short) value;
        }
        if (o instanceof Byte) {
            return (byte) value;
        }
        return value;
    }

    public static final Object doBitAnd(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() & n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't & !");
    }

    public static final Object doBitOr(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() | n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't | !");
    }

    public static final Object doBitXor(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() ^ n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't ^ !");
    }

    public static final Object doBitLm(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() << n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't << !");
    }

    public static final Object doBitRm(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() >> n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't >> !");
    }

    public static final Object doBitZrm(final AbstractStatement statement, final Object o1, final Object o2) throws Exception {
        if (o1 instanceof Number && o2 instanceof Number) {
            final Number n1 = (Number) o1, n2 = (Number) o2;
            return bitCast(n1, n2, n1.longValue() >>> n2.longValue());
        }
        throw new ExecuteException(statement, "Object[" + o1 + "," + o2 + "] was not number meanwhile, can't >>> !");
    }

    public static final Object doBitNot(final AbstractStatement statement, final Object o) throws Exception {
        if (o instanceof Number) {
            return bitNotCast((Number) o);
        }
        throw new ExecuteException(statement, "Object[" + o + "] was not number, can't ~ !");
    }

    private static final Object bitCast(final Number o1, final Number o2, final long value) {
        if (o1 instanceof Long || o2 instanceof Long) {
            return value;
        }
        if (o1 instanceof Integer || o2 instanceof Integer) {
            return (int) value;
        }
        if (o1 instanceof Short || o2 instanceof Short) {
            return (short) value;
        }
        if (o1 instanceof Byte || o2 instanceof Byte) {
            return (byte) value;
        }
        return value;
    }

    private static final Object bitNotCast(final Number o) {
        final long value = ~o.longValue();
        if (o instanceof Long) {
            return value;
        }
        if (o instanceof Integer) {
            return (int) value;
        }
        if (o instanceof Short) {
            return (short) value;
        }
        if (o instanceof Byte) {
            return (byte) value;
        }
        return value;
    }
}
