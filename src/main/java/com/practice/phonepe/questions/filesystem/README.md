# In-memory File System

**Model files and folders.**

- A directory holds files and other directories, nested to any depth.
- Get the total size of any directory (the sum of everything inside it).
- Print the tree with indentation.
- Find a file or folder by name anywhere below a directory.

**Design:** Composite pattern. `File` and `Directory` share `FileSystemComponent`, so size, display and find
treat both the same way. Run `FileSystemDemo`.
