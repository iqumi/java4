package com.lab6.reflection;

import com.lab6.annotations.Repeat;

import java.lang.reflect.Method;

public class Invoker {

    public static void invokeAnnotatedMethods() {
        MyClass obj = new MyClass();
        Class<?> cls = MyClass.class;

        for (Method method : cls.getDeclaredMethods()) {
            // смотрим только protected и private методы
            int modifiers = method.getModifiers();
            boolean isProtected = java.lang.reflect.Modifier.isProtected(modifiers);
            boolean isPrivate = java.lang.reflect.Modifier.isPrivate(modifiers);

            if ((isProtected || isPrivate) && method.isAnnotationPresent(Repeat.class)) {
                Repeat r = method.getAnnotation(Repeat.class);
                method.setAccessible(true);

                Object[] args = buildArgsForMethod(method);

                for (int i = 0; i < r.times(); i++) {
                    try {
                        Object res = method.invoke(obj, args);
                        // если метод возвращает значение — выводим его
                        if (method.getReturnType() != void.class) {
                            System.out.println("Returned: " + res);
                        }
                    } catch (Exception e) {
                        System.err.println("Error invoking method " + method.getName() + ": " + e);
                    }
                }
            }
        }
    }

    private static Object[] buildArgsForMethod(Method method) {
        Class<?>[] params = method.getParameterTypes();
        Object[] args = new Object[params.length];
        for (int i = 0; i < params.length; i++) {
            Class<?> p = params[i];
            if (p == String.class) args[i] = "Sergey";
            else if (p == int.class || p == Integer.class) args[i] = 5;
            else if (p == long.class || p == Long.class) args[i] = 5L;
            else if (p == double.class || p == Double.class) args[i] = 1.0;
            else args[i] = null;
        }
        return args;
    }
}
