package com.practice.phonepe.questions.filesystem;

import java.util.Optional;

/** Leaf: a file has no children. Its size is just its own content size. */
public class File implements FileSystemComponent {

    private final String name;
    private final long sizeInBytes;

    public File(String name, long sizeInBytes) {
        this.name = name;
        this.sizeInBytes = sizeInBytes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        return sizeInBytes;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "- " + name + " (" + sizeInBytes + " bytes)");
    }

    @Override
    public Optional<FileSystemComponent> find(String targetName) {
        return name.equals(targetName) ? Optional.of(this) : Optional.empty();
    }
}
