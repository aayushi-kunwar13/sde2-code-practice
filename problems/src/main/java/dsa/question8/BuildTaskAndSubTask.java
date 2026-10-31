/**
 * Problem: Task / Sub-task Hierarchy
 * Difficulty: Medium
 * Topic: Hashing / Sorting
 * Question: Print tasks by date with their sub-tasks underneath, using |- and \- tree prefixes. Full statement in README.md.
 */
package dsa.question8;

import java.util.*;

public class BuildTaskAndSubTask {

    public static class Item {
        private String date, id, name;
        public Item(String date, String id, String name) {
            this.date = date; this.id = id ; this.name = name;
        }
    }

    public static List<String> printTaskHierarchy(String[] lines) {

        int n = lines.length , i=0;
        HashMap<String, List<Item>> subTaskAndTaskMapping = new HashMap<>();
        List<Item> tasks = new ArrayList<>();

        for(i=0;i<n;i++) {
            String[] line = lines[i].split(",");
            String taskType = line[1];
            if(taskType.equalsIgnoreCase("subtask")) { // meaning subtask
                String parentTaskId = line[3] , subTaskId = line[2] , date = line[0] , name = line[4];
                Item subTask = new Item(date, subTaskId , name);
                subTaskAndTaskMapping.computeIfAbsent(parentTaskId , k -> new ArrayList<>()).add(subTask);

            }else{
                String taskId = line[2] , date = line[0] , name = line[3];
                Item task = new Item(date, taskId , name);
                tasks.add(task);
            }
        }

        // sort on task
        tasks.sort(Comparator.comparing( (Item a) -> a.date)
                        .thenComparing(a -> a.id));

        List<String> ans = new ArrayList<>();

        for(i=0;i<tasks.size();i++) {
            ans.add(tasks.get(i).name);

            if(subTaskAndTaskMapping.containsKey(tasks.get(i).id)) {
                List<Item> subTaskList = subTaskAndTaskMapping.get(tasks.get(i).id);
                subTaskList.sort(Comparator.comparing( (Item a) -> a.date)
                        .thenComparing(a -> a.id));

                for(int j=0; j<subTaskList.size(); j++) {
                    if(j == subTaskList.size()-1) {
                        ans.add("\\- " + subTaskList.get(j).name);
                    }else{
                        ans.add("|- " + subTaskList.get(j).name);
                    }
                }
            }
        }

        return ans;
    }
}
