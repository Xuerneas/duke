import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        String greet = "Hello! I'm Duke\n" +
                "What can I do for you?";

        System.out.println(greet);
        Duke duke = new Duke();

        while (flag) {
            try {
                duke.run();
            } catch(IllegalDukeDescriptionException|IllegalDukeFormatException|IllegalArgumentException e) {
                System.out.println(e.getMessage());

            }
        }

    }
    private static boolean flag = true;
    private static void run() throws IllegalDukeDescriptionException, IllegalDukeFormatException, IllegalArgumentException {
        Scanner sc = new Scanner(System.in);

        Texts texts = new Texts();
        outerloop:
        while(sc.hasNext()) {
            String next = sc.next();
            try {
                switch (Command.valueOf(next)) {
                    case list:
                        String tasks = "Here are the tasks in your list:";
                        System.out.println(tasks);
                        texts.print();
                        break;
                    case bye:
                        String Exit = "Bye. Hope to see you again soon!";
                        System.out.println(Exit);
                        flag = false;
                        break outerloop;
                    case done:
                        int n = sc.nextInt();
                        texts.get(n - 1).setDone();
                        String done = "Nice! I've marked this task as done:\n";
                        System.out.println(done + "  " + texts.get(n - 1));
                        break;
                    case todo:
                        String descriptionT = sc.nextLine();
                        if (descriptionT.isBlank()) {
                            throw new IllegalDukeDescriptionException("todo");
                        } else {
                            Task todo = new Todo(descriptionT);
                            texts.add(todo);
                            System.out.println("Got it. I've added this task:\n" + "  "
                                    + todo + "\nNow you have " +
                                    texts.getNumber() + " tasks in the list.");
                        }
                        break;
                    case deadline:
                        String allD = sc.nextLine();
                        if (allD.contains("/by")) {
                            int placeD = allD.indexOf("/");
                            String descriptionD = allD.substring(0, placeD - 1);
                            String dateD = allD.substring(placeD + 3, allD.length());
                            if (descriptionD.isBlank() || dateD.isBlank()) {
                                throw new IllegalDukeDescriptionException("deadline");
                            } else {
                                Task deadline = new Deadline(descriptionD, dateD);
                                texts.add(deadline);
                                System.out.println("Got it. I've added this task:\n" + "  "
                                        + deadline + "\nNow you have " +
                                        texts.getNumber() + " tasks in the list.");
                            }
                        } else {
                            throw new IllegalDukeFormatException("deadline", "/by");
                        }


                        break;
                    case event:
                        String allE = sc.nextLine();
                        if (allE.contains("/at")) {
                            int placeE = allE.indexOf("/");
                            String descriptionE = allE.substring(0, placeE - 1);
                            String dateE = allE.substring(placeE + 3, allE.length());
                            if (descriptionE.isBlank() || dateE.isBlank()) {
                                throw new IllegalDukeDescriptionException("event");
                            } else {
                                Task event = new Event(descriptionE, dateE);
                                texts.add(event);
                                System.out.println("Got it. I've added this task:\n" + "  "
                                        + event + "\nNow you have " +
                                        texts.getNumber() + " tasks in the list.");
                            }
                        } else {
                            throw new IllegalDukeFormatException("event", "/at");
                        }
                        break;
                    case delete:
                        int indexD = sc.nextInt() - 1;
                        Task t = texts.remove(indexD);
                        System.out.println("Noted. I've removed this task: \n" +
                                "  " + t +
                                "\nNow you have " + texts.getNumber() + " tasks in the list.");
                }
            } catch(IllegalArgumentException e) {
                throw new IllegalDukeArgumentException();
            }
        }
    }

}

enum Command {
    list,bye,done,todo,deadline,event,delete;
        }
