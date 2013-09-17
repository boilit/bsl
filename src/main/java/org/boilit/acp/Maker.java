package org.boilit.acp;

import org.boilit.asm.ClassWriter;
import org.boilit.asm.MethodVisitor;
import org.boilit.asm.Opcodes;
import org.boilit.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Maker {

    /**
     * 生成属性的代理类定义，若属性是非public的则转入getter方法，若getter方法不存在则无法代理
     *
     * @param proxyPackage 代理类所在的包名称
     * @param clazz        被代理类
     * @param fieldName    属性名称
     * @return 代理类定义
     */
    public static final Define makeField(String proxyPackage, final Class clazz, final String fieldName) {
        final Field field = Utilities.getField(clazz, fieldName);
        if (field == null) {
            throw new RuntimeException("Field[" + fieldName + "] was not exist!");
        }
        if (proxyPackage == null || proxyPackage.trim().length() == 0) {
            proxyPackage = Utilities.getProxyPackage();
        }
        Object publicStaticFinalFieldValue = null;
        String methodName = null;
        Method method = null;
        final int modifier = field.getModifiers();
        if (Modifier.isPublic(modifier)) {
            // public static final常量提前取值以便返回常量时使用
            if (Modifier.isStatic(modifier) && Modifier.isFinal(modifier)) {
                try {
                    publicStaticFinalFieldValue = field.get(clazz);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Field[" + fieldName + "]'s const value fetch error!");
                }
            }
        } else {
            // 非public字段映射到public方法
            methodName = String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
            if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
                methodName = "is" + methodName;
            } else {
                methodName = "get" + methodName;
            }
            try {
                method = clazz.getMethod(methodName, new Class[]{});
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Field[" + fieldName + "]'s getter method was not exist!");
            }
            // 拒绝返回值类型不匹配的method
            if (!Utilities.classMatch(field.getType(), method.getReturnType())) {
                throw new RuntimeException("Field[" + fieldName + "]'s getter method return type not matched!");
            }
            // 字段与方法对应权限验证
            // 拒绝非public方法
            if (!Modifier.isPublic(method.getModifiers())) {
                throw new RuntimeException("Field[" + fieldName + "]'s getter method was not public method!");
            }
            // 拒绝静态字段与非静态方法组合
            if (Modifier.isStatic(modifier) && !Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException("Field[" + fieldName + "] is static and it's getter method is no-static!");
            }
            // 拒绝非静态字段与静态方法组合
            if (!Modifier.isStatic(modifier) && Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException("Field[" + fieldName + "] is non-static and it's getter method is static!");
            }
        }
        // 被代理类名
        final String className = clazz.getName().replace('.', '/');
        // 代理类名
        final String proxyClassName = Utilities.toProxyClassName(proxyPackage, className);
        // 创建ClassWriter
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // 创建类
        cw.visit(Utilities.VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_SUPER,
                proxyClassName, null, Utilities.SUPER, null);
        // 创建默认构造方法
        Utilities.doDefaultConstructor(cw);
        // 创建方法
        final MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_VARARGS,
                "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null,
                new String[]{"java/lang/Exception"}
        );
        mv.visitCode();
        if (Modifier.isPublic(modifier)) {
            if (Modifier.isStatic(modifier)) {
                if (Modifier.isFinal(modifier)) {
                    mv.visitLdcInsn(publicStaticFinalFieldValue);
                    Utilities.doPackPrimitive(field.getType(), mv);
                } else {
                    mv.visitFieldInsn(Opcodes.GETSTATIC, className, fieldName, Type.getDescriptor(field.getType()));
                    Utilities.doPackPrimitive(field.getType(), mv);
                }
            } else {
                // 调出第一个参数（第一个临时变量）
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                Utilities.doClassCast(mv, Type.getType(clazz));
                mv.visitFieldInsn(Opcodes.GETFIELD, className, fieldName, Type.getDescriptor(field.getType()));
                Utilities.doPackPrimitive(field.getType(), mv);
            }
        } else {
            final String rtnTypeDescriptor = Type.getDescriptor(method.getReturnType());
            if (Modifier.isStatic(method.getModifiers())) {
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, methodName, "()" + rtnTypeDescriptor);
                Utilities.doPackPrimitive(field.getType(), mv);
            } else {
                // 调出第一个参数（第一个临时变量）
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                Utilities.doClassCast(mv, Type.getType(clazz));
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName, "()" + rtnTypeDescriptor);
                Utilities.doPackPrimitive(field.getType(), mv);
            }
        }
        // 返回结果
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();
        final Define define = new Define();
        define.setClassName(className);
        define.setProxyClassName(proxyClassName);
        define.setTargetName(fieldName);
        define.setMethod(false);
        define.setRedirect(methodName);
        define.setReturnType(field.getType());
        define.setBytes(cw.toByteArray());
        return define;
    }

    /**
     * 生成方法的代理类定义
     *
     * @param proxyPackage 代理类所在包名称
     * @param clazz        被代理类
     * @param methodName   方法名称
     * @param parameters   方法参数类型列表
     * @return 代理类定义
     */
    public static final Define makeMethod(String proxyPackage, final Class clazz, final String methodName, Class... parameters) {
        parameters = parameters == null ? new Class[0] : parameters;
        final Method method = Utilities.getMethod(clazz, methodName, parameters);
        if (method == null) {
            throw new RuntimeException("Method[" + methodName + "] was not exist!");
        }
        if (proxyPackage == null || proxyPackage.trim().length() == 0) {
            proxyPackage = Utilities.getProxyPackage();
        }
        final int modifier = method.getModifiers();
        // 拒绝非public方法
        if (!Modifier.isPublic(modifier)) {
            throw new RuntimeException("Method[" + methodName + "] was not a public method!");
        }
        // 被代理类名
        final String className = clazz.getName().replace('.', '/');
        // 代理类名
        final String proxyClassName = Utilities.toProxyClassName(proxyPackage, className);
        // 创建ClassWriter
        final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        // 创建类
        cw.visit(Utilities.VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_SUPER,
                proxyClassName, null, Utilities.SUPER, null);
        // 创建默认构造方法
        Utilities.doDefaultConstructor(cw);
        // 创建方法
        final MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_VARARGS,
                "invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", null,
                new String[]{"java/lang/Exception"}
        );
        // 非静态方法使用1号位参数作为实例；静态方法使用类名，不占用参数位
        if (!Modifier.isStatic(modifier)) {
            // 调出第一个参数（第一个临时变量），实例
            mv.visitVarInsn(Opcodes.ALOAD, 1);
            Utilities.doClassCast(mv, Type.getType(clazz));
        }
        // 生成参数列表, 实例方法参数列表因1号位被实例占用需增加偏移量1
        final Class[] parameterTypes = method.getParameterTypes();
        final StringBuilder buffer = new StringBuilder(128);
        // 循环构建参数列表，调出第一个临时变量遍历并强制转换
        buffer.append('(');
        for (int i = 0, n = parameterTypes.length; i < n; i++) {
            // 调出第二个参数（第二个临时变量）
            mv.visitVarInsn(Opcodes.ALOAD, 2);
            // 序号压栈
            mv.visitIntInsn(Opcodes.BIPUSH, i);
            // 组合取值
            mv.visitInsn(Opcodes.AALOAD);
            Type paramType = Type.getType(parameterTypes[i]);
            Utilities.doClassCast(mv, paramType);
            // 加入参数描述
            buffer.append(paramType.getDescriptor());
        }
        buffer.append(')');
        // 加入返回值描述
        buffer.append(Type.getDescriptor(method.getReturnType()));

        if (Modifier.isStatic(modifier)) {
            // 调用类方法，使用类调用
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, className, methodName, buffer.toString());
            Utilities.doPackPrimitive(method.getReturnType(), mv);
        } else {
            // 调用实例方法
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, className, methodName, buffer.toString());
            Utilities.doPackPrimitive(method.getReturnType(), mv);
        }
        // 返回结果
        mv.visitInsn(Opcodes.ARETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();
        cw.visitEnd();
        final Define define = new Define();
        define.setClassName(className);
        define.setProxyClassName(proxyClassName);
        define.setTargetName(methodName);
        define.setMethod(true);
        define.setRedirect(null);
        define.setReturnType(method.getReturnType());
        define.setBytes(cw.toByteArray());
        return define;
    }
}
