package com.lab6.reflection;

import com.lab6.annotations.Repeat;

@SuppressWarnings("unused")
public class MyClass {

    // 🔹 Публичные методы
    public void publicMethod1() {
        System.out.println("publicMethod1 called");
    }

    public void publicMethod2(String msg) {
        System.out.println("publicMethod2 called with: " + msg);
    }

    // 🔹 Защищённые методы
    @Repeat(times = 2)
    protected void protectedAnnotatedNoArgs() {
        System.out.println("protectedAnnotatedNoArgs called");
    }

    protected void protectedNotAnnotated(int x) {
        System.out.println("protectedNotAnnotated called with: " + x);
    }

    // 🔹 Приватные методы с параметрами
    @Repeat(times = 3)
    private void privateAnnotatedOneArg(String who) {
        System.out.println("privateAnnotatedOneArg called for: " + who);
    }

    @Repeat(times = 1)
    private int privateAnnotatedWithReturn(int a, int b) {
        int s = a + b;
        System.out.println("privateAnnotatedWithReturn called, sum=" + s);
        return s;
    }

    private void privateNotAnnotated() {
        System.out.println("privateNotAnnotated called");
    }
}
