package org.boilit.acp;

/**
 * @author Boilit
 * @see
 */
public final class Define {
    // 被代理的类名
    private String className;
    // 生成的代理类名
    private String proxyClassName;
    // 方法或字段名
    private String targetName;
    // 是否是方法，方法true，字段false
    private boolean method;
    // 非public字段转向的方法名称,
    // 不转向方法调用的字段或方法该值为null，
    // 转向方法调用的字段则该值为转向的方法名
    private String redirect;
    // 返回值类型
    private Class returnType;
    // 生成的类字节码
    private byte[] bytes;

    protected Define() {
    }

    public final String getClassName() {
        return className;
    }

    protected final void setClassName(String className) {
        this.className = className;
    }

    public final String getProxyClassName() {
        return proxyClassName;
    }

    protected final void setProxyClassName(String proxyClassName) {
        this.proxyClassName = proxyClassName;
    }

    public final String getTargetName() {
        return targetName;
    }

    protected final void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public final boolean isMethod() {
        return method;
    }

    protected final void setMethod(boolean method) {
        this.method = method;
    }

    public final String getRedirect() {
        return redirect;
    }

    protected final void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public final Class getReturnType() {
        return returnType;
    }

    protected final void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public final byte[] getBytes() {
        return bytes;
    }

    protected final void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
