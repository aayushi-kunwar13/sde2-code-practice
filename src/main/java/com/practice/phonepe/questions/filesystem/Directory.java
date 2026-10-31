package com.practice.phonepe.questions.filesystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Composite: a directory holds other {@link FileSystemComponent}s - files, or nested directories.
 * Every aggregate operation (getSize, display, find) just delegates to each child and combines
 * the results, without caring whether a given child is a leaf or another composite.
 */
public class Directory implements FileSystemComponent {

    private final String name;
    private final List<FileSystemComponent> children = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public void add(FileSystemComponent component) {
        boolean nameTaken = children.stream().anyMatch(child -> child.getName().equals(component.getName()));
        if (nameTaken) {
            throw new IllegalArgumentException(name + " already contains an entry named " + component.getName());
        }
        children.add(component);
    }

    public void remove(FileSystemComponent component) {
        children.remove(component);
    }

    public List<FileSystemComponent> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public long getSize() {
        long total = 0;
        for (FileSystemComponent child : children) {
            total += child.getSize();
        }
        return total;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "+ " + name + "/ (" + getSize() + " bytes)");
        for (FileSystemComponent child : children) {
            child.display(indent + "  ");
        }
    }

    @Override
    public Optional<FileSystemComponent> find(String targetName) {
        if (name.equals(targetName)) {
            return Optional.of(this);
        }
        for (FileSystemComponent child : children) {
            Optional<FileSystemComponent> found = child.find(targetName);
            if (found.isPresent()) {
                return found;
            }
        }
        return Optional.empty();
    }
}
