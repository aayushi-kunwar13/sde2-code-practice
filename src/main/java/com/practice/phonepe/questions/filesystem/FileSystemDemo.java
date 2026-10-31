package com.practice.phonepe.questions.filesystem;

import java.util.Optional;

public class FileSystemDemo {

    public static void main(String[] args) {
        Directory root = new Directory("root");

        Directory src = new Directory("src");
        src.add(new File("Main.java", 1200));
        src.add(new File("Utils.java", 800));

        Directory docs = new Directory("docs");
        docs.add(new File("README.md", 400));

        Directory nested = new Directory("nested");
        nested.add(new File("deep.txt", 50));
        docs.add(nested);

        root.add(src);
        root.add(docs);
        root.add(new File("build.gradle", 300));

        // uniform treatment: root.getSize() doesn't care that some children are files and some
        // are directories - it just asks each child for its size and adds them up
        System.out.println("Total size: " + root.getSize() + " bytes");
        System.out.println();

        root.display("");
        System.out.println();

        find(root, "Utils.java");
        find(root, "deep.txt");
        find(root, "missing.txt");
    }

    private static void find(Directory root, String name) {
        Optional<FileSystemComponent> result = root.find(name);
        System.out.println("find(\"" + name + "\") -> " + (result.isPresent() ? "found" : "not found"));
    }
}
