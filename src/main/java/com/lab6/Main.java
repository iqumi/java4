package com.lab6;

import com.lab6.reflection.Invoker;
import com.lab6.filesystem.FileManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Reflection / Annotations demo");
        Invoker.invokeAnnotatedMethods();

        System.out.println();
        System.out.println("File system demo");
        FileManager fm = new FileManager("Shpagin", "Sergey.txt");
        fm.runAll();
    }
}
