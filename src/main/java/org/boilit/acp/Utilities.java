package org.boilit.acp;

import org.boilit.asm.ClassWriter;
import org.boilit.asm.MethodVisitor;
import org.boilit.asm.Opcodes;
import org.boilit.asm.Type;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Boilit
 * @see
 */
@SuppressWarnings("unchecked")
public final class Utilities {
    // 统一生成代理类的兼容起始JDK版本号
    protected static final int VERSION = Opcodes.V1_5;
    // 统一生成代理类的父类名称
    protected static final String SUPER = Proxy.class.getName().replace('.', '/');
    // 内置的基本类型包装类类型TYPE
    private static final int TYPE_BOOLEAN = Type.getType(Boolean.class).getSort();
    private static final int TYPE_BYTE = Type.getType(Byte.class).getSort();
    private static final int TYPE_CHAR = Type.getType(Character.class).getSort();
    private static final int TYPE_SHORT = Type.getType(Short.class).getSort();
    private static final int TYPE_INTEGER = Type.getType(Integer.class).getSort();
    private static final int TYPE_LONG = Type.getType(Long.class).getSort();
    private static final int TYPE_FLOAT = Type.getType(Float.class).getSort();
    private static final int TYPE_DOUBLE = Type.getType(Double.class).getSort();

    // 用于代理类名称的计数器
    private static long serial = 0;

    /**
     * 获取缺省的代理包名称
     *
     * @return
     */
    public static final String getProxyPackage() {
        return Maker.class.getPackage().getName().concat(".proxies");
    }

    /**
     * 类型比较
     *
     * @param refer  参考类型、参数类型
     * @param target 待比较的目标类型
     * @return
     */
    public static final boolean classMatch(final Class refer, final Class target) {
        if (refer == null) {
            return false;
        }
        if (target == null) {
            return !refer.isPrimitive();
        }
        if (refer.isPrimitive()) {
            final int targetSort = Type.getType(target).getSort();
            switch (Type.getType(refer).getSort()) {
                case Type.BOOLEAN:
                    return Type.BOOLEAN == targetSort || TYPE_BOOLEAN == targetSort;
                case Type.BYTE:
                    return Type.BYTE == targetSort || TYPE_BYTE == targetSort;
                case Type.CHAR:
                    return Type.CHAR == targetSort || TYPE_CHAR == targetSort;
                case Type.SHORT:
                    return Type.SHORT == targetSort || TYPE_SHORT == targetSort;
                case Type.INT:
                    return Type.INT == targetSort || TYPE_INTEGER == targetSort;
                case Type.FLOAT:
                    return Type.FLOAT == targetSort || TYPE_FLOAT == targetSort;
                case Type.LONG:
                    return Type.LONG == targetSort || TYPE_LONG == targetSort;
                case Type.DOUBLE:
                    return Type.DOUBLE == targetSort || TYPE_DOUBLE == targetSort;
            }
            return false;
        } else {
            return refer.isAssignableFrom(target);
        }
    }

    /**
     * 获取属性
     *
     * @param clazz     属性所在类
     * @param fieldName 属性名称
     * @return
     */
    public static final Field getField(Class clazz, final String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
        }
        while (field == null && clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return field;
    }

    /**
     * 获取方法
     *
     * @param clazz      方法所在类
     * @param methodName 方法名称
     * @param parameters 方法参数类型列表
     * @return
     */
    public static final Method getMethod(final Class clazz, final String methodName, Class... parameters) {
        parameters = parameters == null ? new Class[0] : parameters;
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parameters);
        } catch (NoSuchMethodException e) {
        }
        if (method != null) {
            return method;
        }
        Class[] parameterTypes = null;
        final Method[] methods = clazz.getMethods();
        for (int i = 0, m = methods.length; i < m; i++) {
            if (!methodName.equals(methods[i].getName())) {
                continue;
            }
            parameterTypes = methods[i].getParameterTypes();
            if (parameterTypes.length != parameters.length) {
                continue;
            }
            boolean classMatch = true;
            for (int j = 0, n = parameterTypes.length; j < n; j++) {
                if (!classMatch(parameterTypes[j], parameters[j])) {
                    classMatch = false;
                    break;
                }
            }
            if (classMatch) {
                method = methods[i];
                break;
            }
        }
        return method;
    }

    /**
     * 对象列表转类型列表
     *
     * @param parameters
     * @return
     */
    public static final Class[] toClasses(final Object... parameters) {
        if (parameters == null || parameters.length == 0) {
            return new Class[0];
        }
        final Class[] parameterTypes = new Class[parameters.length];
        for (int i = 0, n = parameters.length; i < n; i++) {
            if (parameters[i] == null) {
                parameterTypes[i] = null;
            } else {
                parameterTypes[i] = parameters[i].getClass();
            }
        }
        return parameterTypes;
    }

    /**
     * 转代理类名称
     *
     * @param proxyPackage 代理类所在包名
     * @param className    被代理类的名称
     * @return
     */
    public static final String toProxyClassName(final String proxyPackage, final String className) {
        final String suffix = "_BMC_" + (serial++);
        final int index = className.replace('/', '.').lastIndexOf('.');
        if (index == -1) {
            return proxyPackage.concat(".").concat(className).concat(suffix).replace('.', '/');
        } else {
            return proxyPackage.concat(className.substring(index)).concat(suffix).replace('.', '/');
        }
    }

    /**
     * 创建缺省构造方法
     *
     * @param cw
     */
    protected static final void doDefaultConstructor(final ClassWriter cw) {
        // 默认构造器
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        // 压入this变量
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        // 执行父类构造器
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, SUPER, "<init>", "()V");
        mv.visitInsn(Opcodes.RETURN);
        // 这段代码使用最多0个堆元素 和0个局部变量
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    /**
     * ASM强制类型转换
     *
     * @param mv
     * @param paramType
     */
    protected static final void doClassCast(final MethodVisitor mv, final Type paramType) {
        switch (paramType.getSort()) {
            case Type.BOOLEAN:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
                break;
            case Type.BYTE:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
                break;
            case Type.CHAR:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
                break;
            case Type.SHORT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
                break;
            case Type.INT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
                break;
            case Type.FLOAT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
                break;
            case Type.LONG:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
                break;
            case Type.DOUBLE:
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
                break;
            case Type.ARRAY:
                mv.visitTypeInsn(Opcodes.CHECKCAST, paramType.getDescriptor());
                break;
            case Type.OBJECT:
                mv.visitTypeInsn(Opcodes.CHECKCAST, paramType.getInternalName());
                break;
        }
    }

    /**
     * ASM基本类型转包装类型
     *
     * @param clazz
     * @param mv
     */
    protected static final void doPackPrimitive(final Class clazz, final MethodVisitor mv) {
        switch (Type.getType(clazz).getSort()) {
            case Type.BOOLEAN:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                break;
            case Type.BYTE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                break;
            case Type.CHAR:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                break;
            case Type.SHORT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                break;
            case Type.INT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                break;
            case Type.FLOAT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                break;
            case Type.LONG:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                break;
            case Type.DOUBLE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                break;
        }
    }

    /**
     * 代理类定义输出到指定目录
     *
     * @param define    代理类定义
     * @param directory 输出目录
     */
    public static final void write(final Define define, final File directory) {
        if (define == null || directory == null) {
            return;
        }
        OutputStream outputStream = null;
        try {
            String fileName = define.getProxyClassName().replace('/', File.separatorChar).concat(".class");
            File file = new File(directory, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            outputStream.write(define.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
