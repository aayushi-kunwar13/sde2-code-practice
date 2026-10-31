# Task / Sub-task Hierarchy

**Topic:** Hashing / Sorting · **Difficulty:** Medium

Read lines from stdin, each either a task or a sub-task:
```
date,task,taskId,name
date,subtask,subTaskId,parentTaskId,name
```
Print the tasks ordered by date (then id). Under each task, print its sub-tasks in the same order, using a tree prefix:
`|- ` for every sub-task except the last, and `\- ` for the last.

**Approach:** `HashMap<parentId, List<subtask>>` plus a list of tasks. Sort both by `(date, id)` and print.
