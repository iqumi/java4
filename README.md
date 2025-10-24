# Лабораторная работа №6 — Рефлексия, аннотации и java.nio

ФИО: Шпагин Сергей

Что делает:
1. Демонстрирует пользовательскую аннотацию `@Repeat` и вызов приватных/защищённых методов через Reflection.
2. Создаёт директорию `Shpagin`, внутри файл `Sergey.txt`, создает `dir1/dir2/dir3`, копирует файл, создает file1 и file2, рекурсивно выводит структуру и удаляет `dir1`.

Файлы:
- src/main/java/com/lab6/annotations/Repeat.java — определение аннотации
- src/main/java/com/lab6/reflection/MyClass.java — класс с методами (public/protected/private) и аннотациями
- src/main/java/com/lab6/reflection/Invoker.java — вызывает защищённые и приватные аннотированные методы
- src/main/java/com/lab6/filesystem/FileManager.java — операции с java.nio.file
- src/main/java/com/lab6/Main.java — запускает Invoker и FileManager

