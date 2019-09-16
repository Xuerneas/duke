package duke.command;

import duke.excaptions.IllegalDukeArgumentException;
import duke.excaptions.IllegalDukeFormatException;

import duke.list.TaskList;


import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

/**
 * The Parser class is one of the class in command package which used to deal with making sense of the user command
 */
public class Parser {
    private String[] splitCommand;

    /**
     * Constructor to write in command line
     *
     * @param splitCommand
     */
    public Parser(String[] splitCommand) {
        this.splitCommand = splitCommand;
    }

    /**
     * this method is to deal with different commands
     *
     * @param storage the object to interact with
     * @throws IllegalDukeFormatException
     * @throws IllegalDukeArgumentException
     */
    public String parse(Storage storage) throws IllegalDukeFormatException, IllegalDukeArgumentException, ParseException {
        switch (Command.valueOf(splitCommand[0])) {
            case list:
                return commandList();
            case bye:
                return commandBye();
            case done:
                return commandDone(storage);
            case todo:
                return commandTodo(storage);
            case deadline:
                return commandDeadline(storage);
            case event:
                return commandEvent(storage);
            case delete:
                return commandDelete(storage);
            case find:
                return commandFind();
            case update:
                return commandUpdate(storage);
        }
        return "";
    }

    /**
     * method for "list" command, return the list
     */
    private String commandList() {
        String tasks = "Here are the tasks in your list:\n";
        System.out.println(tasks);
        return tasks + TaskList.listString() + "\n";
    }

    /**
     * method for "bye" command, return ending and set isExit to true
     */
    private String commandBye() {
        String Exit = "Bye. Hope to see you again soon!\n";
        return Exit;
    }

    /**
     * method for "done" command, set the task done, and rewrite them to text file with the status updated.
     *
     * @param storage the object to interact with
     */
    private String commandDone(Storage storage) {
        try {
            int index = Integer.parseInt(splitCommand[1]);
            TaskList.getList().get(index - 1).setDone();
            String done = "Nice! I've marked this task as done:\n";
            boolean isAppend = false;
            if (TaskList.getList().size() > 0) {
                for (Task t : TaskList.getList()) {
                    storage.textWrite(t.toString(), isAppend);
                    isAppend = true;
                }
            } else {
                storage.textWrite("", false);
            }
            return done + "  " + TaskList.getList().get(index - 1) + "\n";
        } catch (IndexOutOfBoundsException e) {
            return "Selected index not exists" + "\n";
        }

    }

    /**
     * method for "todo" command, add the todo task into the list and write it in text file
     *
     * @param storage the object to interact with
     * @throws IllegalDukeArgumentException
     */
    private String commandTodo(Storage storage) throws IllegalDukeArgumentException {
        try {
            String descriptionT = splitCommand[1];
            assert descriptionT.length() > 0 : "invalid description";
            Task todo = new Todo(descriptionT);
            TaskList.getList().add(todo);
            storage.textWrite(todo.toString(), true);
            return "Got it. I've added this task:\n" + "  "
                    + todo + "\nNow you have " + TaskList.getList().size() + " tasks in the list." + "\n";
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalDukeArgumentException();
        }

    }

    /**
     * method for "deadline" command, add the deadline task into the list and write it in text file
     *
     * @param storage the object to interact with
     * @throws IllegalDukeFormatException
     */
    private String commandDeadline(Storage storage) throws IllegalDukeFormatException {
        try {
            String[] fullCommand = splitCommand[1].split(" /by ");
            String deadlineTime = fullCommand[1];
            assert deadlineTime.length() > 0 : "invalid time";
            Task deadline = new Deadline(fullCommand[0], deadlineTime);
            TaskList.getList().add(deadline);
            storage.textWrite(deadline.toString(), true);
            return "Got it. I've added this task:\n" + "  "
                    + deadline + "\nNow you have " +
                    TaskList.getList().size() + " tasks in the list." + "\n";
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalDukeFormatException("deadline");
        } catch (ParseException e) {
            return "OOPS!!! Wrong time format!" + "\n";
        }
    }

    /**
     * method for "event" command, add the event task into the list and write it in text file
     *
     * @param storage the object to interact with
     * @throws IllegalDukeFormatException
     */
    private String commandEvent(Storage storage) throws IllegalDukeFormatException {
        try {
            String[] fullCommand = splitCommand[1].split(" /at ");
            String eventTime = fullCommand[1];
            assert eventTime.length() > 0 : "invalid time";
            Task event = new Event(fullCommand[0], eventTime);
            TaskList.getList().add(event);
            storage.textWrite(event.toString(), true);
            return "Got it. I've added this task:\n" + "  "
                    + event + "\nNow you have " +
                    TaskList.getList().size() + " tasks in the list." + "\n";
        } catch (ParseException e) {
            return "OOPS!!! Wrong time format!" + "\n";
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalDukeFormatException("event");
        }
    }

    /**
     * method for "delete" command, delete the task by remove it from the taskList and rewrite list to file
     *
     * @param storage the object to interact with
     */
    private String commandDelete(Storage storage) {
        try {
            int indexD = Integer.parseInt(splitCommand[1]) - 1;
            Task removedTask = TaskList.getList().remove(indexD);
            boolean isAppend = false;
            if (TaskList.getList().size() > 0) {
                for (Task t : TaskList.getList()) {
                    storage.textWrite(t.toString(), isAppend);
                    isAppend = true;
                }
            } else {
                storage.textWrite("", false);
            }
            return "Noted. I've removed this task: \n" + "  "
                    + removedTask + "\nNow you have " + TaskList.getList().size() + " tasks in the list." + "\n";
        } catch (IndexOutOfBoundsException e) {
            return "Selected index not exists" + "\n";
        }
    }

    private String commandFind() {
        String target = splitCommand[1];
        String reply = "Here are the matching tasks in your list:\n";
        String list = "";
        boolean isFound = false;
        LinkedList<Task> targetList = new LinkedList<>();
        for (Task task : TaskList.getList()) {
            if (task.toString().contains(target)) {
                targetList.add(task);
                isFound = true;
            }
        }
        if (isFound) {
            for (int i = 0; i < targetList.size(); i++) {
                int index = i + 1;
                list += "  " + index + "." + targetList.get(i).toString() + "\n";
            }
            assert list.length() > 0 : "empty list";
            return reply + list;
        } else {
            return "OOPS! No such key word detected." + "\n";
        }
    }

    private String commandUpdate(Storage storage) throws ParseException {
        try {
            String[] updateMessage = splitCommand[1].split(" ", 2);
            int indexU = Integer.parseInt(updateMessage[0]) - 1;
            Task updatedTask = TaskList.getList().remove(indexU);
            String classThatUpdating = updatedTask.getClass().getName();
            String description = updatedTask.getDescription();
            String updatedTime = updateMessage[1];
            System.out.println(classThatUpdating);
            if (classThatUpdating.equals("duke.task.Deadline")) {
                Deadline updatedDeadline = new Deadline(description, updatedTime);
                TaskList.getList().add(indexU, updatedDeadline);
            } else if (classThatUpdating.equals("duke.task.Event")) {
                Event updatedEvent = new Event(description, updatedTime);
                TaskList.getList().add(indexU, updatedEvent);
            }
            boolean isAppend = false;
            if (TaskList.getList().size() > 0) {
                for (Task t : TaskList.getList()) {
                    storage.textWrite(t.toString(), isAppend);
                    isAppend = true;
                }
            } else {
                storage.textWrite("", false);
            }
            return "Task updated!\n";
        } catch (IndexOutOfBoundsException e) {
            return "OOPS! Error detected!\nCheck your command format or index of task.\n";
        }
    }
}

/**
 * the enum class with all types of command
 */
enum Command {
    list,bye,done,todo,deadline,event,delete,find,update;
}
