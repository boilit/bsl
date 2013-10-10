package org.boilit.acp;

import java.io.File;

/**
 * @author Boilit
 * @see
 */
public final class ACP {
    public static final Proxy proxyField(final ClassLoader classLoader, final Class clazz,
                                         final String fieldName) throws Exception {
        return proxyField(classLoader, Utilities.getProxyPackage(), clazz, fieldName);
    }

    public static final Proxy proxyMethod(final ClassLoader classLoader, final Class clazz,
                                          final String methodName, final Object[] parameters) throws Exception {
        return proxyMethod(classLoader, Utilities.getProxyPackage(), clazz, methodName, parameters);
    }

    public static final Proxy proxyField(final ClassLoader classLoader, final String proxyPackage, final Class clazz,
                                         final String fieldName) throws Exception {
        return (Proxy) PCL.get(classLoader).defineClass(Maker.makeField(proxyPackage, clazz, fieldName)).newInstance();
    }

    public static final Proxy proxyMethod(final ClassLoader classLoader, final String proxyPackage, final Class clazz,
                                          final String methodName, final Object[] parameters) throws Exception {
        final Class[] parameterTypes;
        if (parameters instanceof Class[]) {
            parameterTypes = (Class[]) parameters;
        } else {
            parameterTypes = Utilities.toClasses(parameters);
        }
        return (Proxy) PCL.get(classLoader).defineClass(Maker.makeMethod(proxyPackage, clazz, methodName, parameterTypes)).newInstance();
    }
}
