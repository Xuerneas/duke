package duke.list;

import duke.task.Task;

import java.util.LinkedList;

public class TaskList extends LinkedList<Task> {
    private static LinkedList<Task> taskList;

    public TaskList() {
        taskList = new LinkedList<>();
    }

    public TaskList(LinkedList<Task> taskList) {
        TaskList.taskList = taskList;
    }

    public static LinkedList<Task> getList() {
        return taskList;
    }

    public static void print() {
        for (int i = 0; i < taskList.size(); i++) {
            int index = i + 1;
            Task t = taskList.get(i);
            System.out.println(index + "." + t);
        }


    }
}