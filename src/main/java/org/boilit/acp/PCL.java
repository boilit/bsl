
package org.boilit.acp;

import java.lang.reflect.Method;

/**
 * @author Boilit
 * @see
 */
public final class PCL extends ClassLoader {
    public static final PCL get(final ClassLoader parent) {
        return new PCL(parent);
    }

    private PCL(final ClassLoader parent) {
        super(parent);
    }

    public final Class<?> loadClass(final String name) throws ClassNotFoundException {
        // These classes come from the classLoader that loaded ProxyClassLoader.
        if (name.equals(Proxy.class.getName())) return Proxy.class;
        // All other classes come from the classLoader that loaded the type we are accessing.
        return super.loadClass(name);
    }

    protected final synchronized Class<?> loadClass(final String name, final boolean resolve) throws ClassNotFoundException {
        // These classes come from the classLoader that loaded ProxyClassLoader.
        if (name.equals(Proxy.class.getName())) return Proxy.class;
        // All other classes come from the classLoader that loaded the type we are accessing.
        return super.loadClass(name, resolve);
    }

    public final Class<?> defineClass(final Define define) throws ClassFormatError {
        return defineClass(define.getProxyClassName(), define.getBytes());
    }

    public final Class<?> defineClass(String name, final byte[] bytes) throws ClassFormatError {
        name = name.replace('/', '.');
        try {
            // Attempt to load the access class in the same loader, which makes protected and default access members accessible.
            Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[]{
                    String.class, byte[].class, int.class, int.class
            });
            method.setAccessible(true);
            return (Class) method.invoke(this.getParent(), new Object[]{
                    name, bytes, Integer.valueOf(0), Integer.valueOf(bytes.length)
            });
        } catch (Exception ignore) {
        }
        return super.defineClass(name, bytes, 0, bytes.length);
    }
}
