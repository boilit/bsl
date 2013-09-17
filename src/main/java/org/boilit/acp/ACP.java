package org.boilit.acp;

import java.io.File;

/**
 * @author Boilit
 * @see
 */
public final class ACP {
    public static final Proxy proxyField(final Class clazz, final String fieldName) throws Exception {
        return proxyField(Utilities.getProxyPackage(), clazz, fieldName);
    }

    public static final Proxy proxyMethod(final Class clazz, final String methodName, final Object[] parameters) throws Exception {
        return proxyMethod(Utilities.getProxyPackage(), clazz, methodName, parameters);
    }

    public static final Proxy proxyField(final String proxyPackage, final Class clazz, final String fieldName) throws Exception {
        final Define define = Maker.makeField(proxyPackage, clazz, fieldName);
        final PCL pcl = PCL.get(Thread.currentThread().getContextClassLoader());
        return (Proxy) pcl.defineClass(define).newInstance();
    }

    public static final Proxy proxyMethod(final String proxyPackage, final Class clazz, final String methodName, final Object[] parameters) throws Exception {
        final Class[] parameterTypes;
        if (parameters instanceof Class[]) {
            parameterTypes = (Class[]) parameters;
        } else {
            parameterTypes = Utilities.toClasses(parameters);
        }
        final Define define = Maker.makeMethod(proxyPackage, clazz, methodName, parameterTypes);
        final PCL pcl = PCL.get(Thread.currentThread().getContextClassLoader());
        return (Proxy) pcl.defineClass(define).newInstance();
    }
}
