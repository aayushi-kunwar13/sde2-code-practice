package com.practice.phonepe.questions.filesystem;

import java.util.Optional;

/**
 * The Composite pattern's shared contract. A {@link File} (leaf) and a {@link Directory}
 * (composite of many components) both look like "a thing with a name and a size" to any caller,
 * so code that walks the tree - getSize, display, find - never needs to know which one it holds.
 *
 * Deliberately does NOT declare add/remove here. The classic GoF version puts them on this
 * interface and has File throw UnsupportedOperationException for them - "transparent" to callers,
 * but it means every File carries two methods that never make sense to call. Keeping them on
 * Directory only means the compiler stops you from calling directory.add(...) on a File, at the
 * cost of callers needing an `instanceof Directory` check if they specifically want to add a
 * child to whatever they're holding.
 */
public interface FileSystemComponent {
    String getName();
    long getSize();
    void display(String indent);
    Optional<FileSystemComponent> find(String name);
}
