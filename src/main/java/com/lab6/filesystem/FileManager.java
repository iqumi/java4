package com.lab6.filesystem;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileManager {

    private final Path rootDir;
    private final Path nameFile;

    public FileManager(String surname, String name) {
        this.rootDir = Paths.get(surname);
        this.nameFile = rootDir.resolve(name);
    }

    public void runAll() {
        try {
            createStructure();
            copyToNested();
            createExtraFiles();
            walkAndPrint();
            deleteDir1();
            System.out.println("FileManager: finished successfully");
        } catch (IOException e) {
            System.err.println("FileManager error: " + e);
        }
    }

    private void createStructure() throws IOException {
        // создать корневую директорию и вложенные dir1/dir2/dir3
        Path dir123 = rootDir.resolve("dir1/dir2/dir3");
        Files.createDirectories(dir123);

        if (!Files.exists(rootDir)) {
            Files.createDirectories(rootDir);
        }
        Files.writeString(nameFile, "This is a test file for Sergey Shpagin\n", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("Created structure and file: " + nameFile.toAbsolutePath());
    }

    private void copyToNested() throws IOException {
        Path target = rootDir.resolve("dir1/dir2/dir3/" + nameFile.getFileName().toString());
        Files.copy(nameFile, target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copied file to: " + target.toAbsolutePath());
    }

    private void createExtraFiles() throws IOException {
        Path file1 = rootDir.resolve("dir1/file1.txt");
        Path file2 = rootDir.resolve("dir1/dir2/file2.txt");
        Files.writeString(file1, "file1 in dir1", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Files.writeString(file2, "file2 in dir2", StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Created file1 and file2");
    }

    private void walkAndPrint() throws IOException {
        System.out.println("Walking directory: " + rootDir.toAbsolutePath());
        try (Stream<Path> stream = Files.walk(rootDir)) {
            stream.forEach(p -> {
                if (Files.isDirectory(p)) {
                    System.out.println("D: " + p.toAbsolutePath());
                } else {
                    System.out.println("F: " + p.toAbsolutePath());
                }
            });
        }
    }

    private void deleteDir1() throws IOException {
        Path dir1 = rootDir.resolve("dir1");
        if (!Files.exists(dir1)) {
            System.out.println("dir1 does not exist, nothing to delete");
            return;
        }
        // удаляем рекурсивно — сначала файлы, затем директории
        try (Stream<Path> walk = Files.walk(dir1)) {
            walk.sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
        System.out.println("Deleted dir1 and its contents");
    }
}
