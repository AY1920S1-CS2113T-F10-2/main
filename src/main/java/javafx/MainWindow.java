package javafx;

import degree.DegreeManager;
import exception.DukeException;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import list.DegreeList;
import main.Duke;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import module.ConjunctiveModule;
import module.Module;
import module.ModuleList;
import module.NonDescriptive;
import task.Task;
import task.TaskList;

import java.util.*;


/**
 * Controller for JavaFX.MainWindow. Provides the layout for the other controls.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Label diffDegreeLabel1;
    @FXML
    private Label diffDegreeLabel2;

    @FXML
    private TableView<TaskFX> taskView;
    @FXML
    private TableView<ChoicesFX> choicesView;
    @FXML
    private TableView<DegreesFX> degreesView;
    @FXML
    private TableView<DiffFX> diffView1;
    @FXML
    private TableView<DiffFX> diffView2;
    @FXML
    private TableView<SimiFX> simiView;
    @FXML
    private TableView<HelpFX> helpView;
    @FXML
    private TableView<HelpFX> keywordView;

    @FXML
    private TabPane tabPane;
    @FXML
    private GridPane gridPane;

    @FXML
    private Tab tabKeywords;
    @FXML
    private Tab tabTask;
    @FXML
    private Tab tabChoices;
    @FXML
    private Tab tabDegrees;
    @FXML
    private Tab tabDiff;
    @FXML
    private Tab tabHelp;

    @FXML
    private TableColumn helpCommandCol;
    @FXML
    private TableColumn keywordCol;

    private Duke duke;
    private TaskList taskList;
    private DegreeList degreeList;
    private DegreeManager degreeManager;
    private ModuleList moduleList;
    private boolean typoFlag;

    private Set<String> autoSuggestion = new HashSet<>(Arrays.asList("help", "detail", "compare", "add", "swap",
            "delete", "bye", "undo", "redo",  "schedule", "event", "todo", "deadline", "view_employment",
            "cohort_size", "done", "choices", "find", "remove", "snooze", "sort", "tasks", "keywords"));
    private SuggestionProvider<String> provider = SuggestionProvider.create(autoSuggestion);

    private ObservableList<TaskFX> dataTask;
    private ObservableList<ChoicesFX> dataChoices;
    private ObservableList<DegreesFX> dataDegrees;
    private ObservableList<DiffFX> dataDiff1;
    private ObservableList<DiffFX> dataDiff2;
    private ObservableList<SimiFX> dataSimi;
    private ObservableList<HelpFX> dataHelp;
    private ObservableList<HelpFX> dataKeywords;


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Method that runs when duke GUI is started up.
     * It does not take any input from the terminal yet, and does not output to the terminal either.
     *
     * @param d The duke helper that is going to be initialized
     */
    //This method initializes duke
    public void setDuke(Duke d) throws DukeException {

        duke = d;


        //new AutoCompletionTextFieldBinding<>(this.userInput, provider).setVisibleRowCount(1);
        new AutoCompletionTextFieldBinding<>(this.userInput, provider).setDelay(50); //50ms

        String logo = "  _____  ______ _____ _____  ______ ______  _____  _   _ _    _  _____ \n"
                + " |  __ \\|  ____/ ____|  __ \\|  ____|  ____|/ ____|| \\ | | |  | |/ ____|\n"
                + " | |  | | |__ | |  __| |__) | |__  | |__  | (___  |  \\| | |  | | (___  \n"
                + " | |  | |  __|| | |_ |  _  /|  __| |  __|  \\___ \\ | . ` | |  | |\\___ \\ \n"
                + " | |__| | |___| |__| | | \\ \\| |____| |____ ____) || |\\  | |__| |____) |\n"
                + " |_____/|______\\_____|_|  \\_\\______|______|_____(_)_| \\_|\\____/|_____/ \n";

        String welcome = logo +"\n";

        String greeting;

        if (duke.reminder().isEmpty()) {
            greeting = "What would you like to do?\n\n";
        } else {
            greeting = duke.reminder() + "\n\n";
        }

        welcome += greeting;

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcome)
        );

        this.dataTask = taskView.getItems();
        this.dataChoices = choicesView.getItems();

        this.taskList = duke.getTaskList();
        this.degreeList = duke.getDegreeList();
        this.degreeManager = duke.getDegreesManager(); //gets the entire degree manager from duke, only done once

        String countString;

        //Initialize the help tab, this is only done once during start-up
        handleHelp();
        handleKeywords();

        this.diffView1.getVisibleLeafColumn(0).setReorderable(false);
        this.diffView1.getVisibleLeafColumn(1).setReorderable(false);
        this.diffView1.getVisibleLeafColumn(2).setReorderable(false);
        this.diffView2.getVisibleLeafColumn(0).setReorderable(false);
        this.diffView2.getVisibleLeafColumn(1).setReorderable(false);
        this.diffView2.getVisibleLeafColumn(2).setReorderable(false);
        this.simiView.getVisibleLeafColumn(0).setReorderable(false);
        this.simiView.getVisibleLeafColumn(1).setReorderable(false);
        this.simiView.getVisibleLeafColumn(2).setReorderable(false);
        this.taskView.getVisibleLeafColumn(0).setReorderable(false);
        this.taskView.getVisibleLeafColumn(1).setReorderable(false);
        this.taskView.getVisibleLeafColumn(2).setReorderable(false);
        this.taskView.getVisibleLeafColumn(3).setReorderable(false);
        this.taskView.getVisibleLeafColumn(4).setReorderable(false);
        this.taskView.getVisibleLeafColumn(5).setReorderable(false);
        this.choicesView.getVisibleLeafColumn(0).setReorderable(false);
        this.choicesView.getVisibleLeafColumn(1).setReorderable(false);
        this.degreesView.getVisibleLeafColumn(0).setReorderable(false);
        this.degreesView.getVisibleLeafColumn(1).setReorderable(false);
        this.degreesView.getVisibleLeafColumn(2).setReorderable(false);
        this.degreesView.getVisibleLeafColumn(3).setReorderable(false);

        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataTask.add(new TaskFX(countString, newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate(), newTask.getUserDefinedPriority()));
        }

        for (int i = 0; i < this.degreeList.size(); i++) {
            String newChoice = this.degreeList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataChoices.add(new ChoicesFX(countString, newChoice));
        }

    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaFX.Main.Duke's
     * reply and then appends them to the dialog container.
     * Clears the user input after processing.
     * Also changes the tables in the GUI after every user input, assuming there are changes.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        typoFlag = false;
        String input = userInput.getText();
        String response = duke.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDukeDialog(response)
        );

        typoFlag = duke.getTypoFlag();
        //Flag to check if there is an error in compare command
        boolean degreeFoundFlag = degreeManager.getFoundFlag();

        //Learn new auto suggestions based on user inputs, and do not learn typos
        if (!autoSuggestion.contains(input) && (!typoFlag) && (degreeFoundFlag)) {
            autoSuggestion.add(input);
            provider.clearSuggestions();
            provider.addPossibleSuggestions(autoSuggestion);
        }


        this.taskList = duke.getTaskList();
        this.degreeList = duke.getDegreeList();

        this.dataTask.clear();
        this.dataChoices.clear();

        String countString;


        //Every time the user inputs something, refresh the task list
        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataTask.add(new TaskFX(countString, newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate(), newTask.getUserDefinedPriority()));
        }

        //Every time the user inputs something, refresh the degree list
        for (int i = 0; i < this.degreeList.size(); i++) {
            String newChoice = this.degreeList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataChoices.add(new ChoicesFX(countString, newChoice));
        }

        //Forces a tab to open corresponding to the input
        if (input.matches("tasks")) {
            tabPane.getSelectionModel().select(tabTask);
        } else if (input.matches("choices")) {
            tabPane.getSelectionModel().select(tabChoices);
        } else if (input.matches("keywords")) {
            tabPane.getSelectionModel().select(tabKeywords);
        } else if (input.matches("help")) {
            tabPane.getSelectionModel().select(tabHelp);
        } else if (input.matches("detail")) {
            tabPane.getSelectionModel().select(tabDegrees);
        } else if (input.matches("compare")) {
            tabPane.getSelectionModel().select(tabDiff);
        } else if (input.matches("bye")) { //If user wants to end program, create a separate thread with a timer to exit
            // delay & exit on other thread
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                System.exit(0);
            }).start();
        } else { //Now for commands with multiple inputs, to swap tabs when the user has modified something in that tab
            Scanner temp = new Scanner(input);
            String command = temp.next();
            if (command.matches("done|event|todo|sort|snooze|deadline|schedule|find|delete") && (!typoFlag)) {
                tabPane.getSelectionModel().select(tabTask);
            } else if (command.matches("swap|remove|add") && (!typoFlag)) {
                tabPane.getSelectionModel().select(tabChoices);
            } else if (command.matches("detail") && (!typoFlag) && (degreeFoundFlag)) {
                handleDetail(temp);
            } else if (command.matches("compare") && (!typoFlag) && (degreeFoundFlag)) {
                handleCompare(temp);
            }
        }

        userInput.clear();
    }

    private void handleDetail(Scanner temp) {
        this.dataDegrees = degreesView.getItems();
        String countString;
        tabPane.getSelectionModel().select(tabDegrees);


        String degreeName = this.degreeManager.findAnyDegree(temp.nextLine().strip());//this.degreeManager.findAnyDegree(temp.next());

        if(!degreeName.isBlank()) {
            this.dataDegrees.clear();
            tabDegrees.setText("Degree Information: " + this.degreeManager.getFullDegreeName(degreeName)); //use these to change the degree tab name

            List<Module> moduleList = new ArrayList<>(this.degreeManager.getModuleList(degreeName).getModules());
            moduleList.add(new NonDescriptive("General Education Modules", 20));

            int count = 0;
            for(Module mod: moduleList) {
                count++;
                countString = Integer.toString(count);

                if (count <= 9) {
                    countString = "0" + countString;
                }

                String mcString = Integer.toString(mod.getMc());

                if (mcString.length() == 1) {
                    mcString = "0" + mcString;
                }

                this.dataDegrees.add(new DegreesFX(countString, mod.tabModuleCode(), mod.tabModuleName(), mcString));
            }
        }
    }


    private void handleCompare(Scanner temp) {
        this.dataDiff1 = diffView1.getItems();
        this.dataDiff2 = diffView2.getItems();
        this.dataSimi = simiView.getItems();

        tabPane.getSelectionModel().select(tabDiff);

        dataDiff1.clear();
        dataDiff2.clear();
        dataSimi.clear();
        String input = temp.nextLine().strip();
        String[] split = input.split("\\s+");
        this.degreeManager.twoKeyGenerator(split);
        String degreeName1 = this.degreeManager.getDegreeOneKey();
        String degreeName2 = this.degreeManager.getDegreeTwoKey();

        ModuleList ModuleList1 = this.degreeManager.getModuleList(degreeName1);
        ModuleList ModuleList2 = this.degreeManager.getModuleList(degreeName2);
        ModuleList diffModuleList2 = this.degreeManager.getModuleList(degreeName2).getDifference(ModuleList1);
        ModuleList diffModuleList1 = this.degreeManager.getModuleList(degreeName1).getDifference(ModuleList2);
        ModuleList simiModuleList = this.degreeManager.getModuleList(degreeName2).getSimilar(ModuleList1);

        List<Module> diffModuleData1 = new ArrayList<>(diffModuleList1.getModules());
        List<Module> diffModuleData2 = new ArrayList<>(diffModuleList2.getModules());
        List<Module> simiModuleData = new ArrayList<>(simiModuleList.getModules());
        simiModuleData.add(new NonDescriptive("General Education Modules", 20));

        diffDegreeLabel1.setText(this.degreeManager.getFullDegreeName(degreeName1));
        diffDegreeLabel2.setText(this.degreeManager.getFullDegreeName(degreeName2));

        //For similar modules
        for (Module mod : simiModuleData) {
            String mcString = Integer.toString(mod.getMc());

            if (mcString.length() == 1) {
                mcString = "0" + mcString;
            }
            this.dataSimi.add(new SimiFX(mod.tabModuleCode(), mod.tabModuleName(), mcString));
        }

        //For first module differs
        for (Module mod : diffModuleData1) {
            String mcString = Integer.toString(mod.getMc());

            if (mcString.length() == 1) {
                mcString = "0" + mcString;
            }
            this.dataDiff1.add(new DiffFX(mod.tabModuleCode(), mod.tabModuleName(), mcString));

        }

        //For second module differs
        for (Module mod : diffModuleData2) {
            String mcString = Integer.toString(mod.getMc());

            if (mcString.length() == 1) {
                mcString = "0" + mcString;
            }
            this.dataDiff2.add(new DiffFX(mod.tabModuleCode(), mod.tabModuleName(), mcString));

        }
    }

    private void handleKeywords() {
        keywordCol.setStyle("-fx-alignment: CENTER-LEFT;");
        this.dataKeywords = keywordView.getItems();
        this.keywordView.getVisibleLeafColumn(0).setReorderable(false);
        this.keywordView.getVisibleLeafColumn(1).setReorderable(false);
        Map<String,List<String>> aliases = degreeManager.getKeywords();
        for(Map.Entry<String, List<String>> entry: aliases.entrySet()) {
            String key = entry.getKey();
            List<String> commonAliases = entry.getValue();
            StringBuilder myAliases = new StringBuilder();
            for(String name: commonAliases) {
                if(!name.equalsIgnoreCase(key))
                    myAliases.append(name).append(", ");
            }
            myAliases.setLength(myAliases.length() - 2);
            this.dataKeywords.add(new HelpFX(key, myAliases.toString()));
        }
        keywordView.sort();
    }


    private void handleHelp() {
        helpCommandCol.setStyle("-fx-alignment: CENTER-LEFT;");
        this.helpView.getVisibleLeafColumn(0).setReorderable(false);
        this.helpView.getVisibleLeafColumn(1).setReorderable(false);
        this.dataHelp = helpView.getItems();
        String description;

        //Add Command.
        description = "Adds a degree to your choices.\n"
                + "Also adds events related to that degree to your tasks.\n\n"
                + "Examples: add ISE | add Industrial and Systems Engineering";
        this.dataHelp.add(new HelpFX("add <degree>", description));

        //Bye Command.
        description = "Exits the Program after a short delay.";
        this.dataHelp.add(new HelpFX("bye", description));

        //tasks Command.
        description = "Displays the current list of tasks.\n"
                + "Will also switch to the \"Tasks\" tab.";
        this.dataHelp.add(new HelpFX("tasks", description));

        //choices Command.
        description = "Displays your current choices of degree.\n"
                + "Will also switch to the \"Degree Choices\" tab.";
        this.dataHelp.add(new HelpFX("choices", description));

        //help Command.
        description = "Displays help for all commands, or a certain command.\n"
                + "Will switch to the \"Help\" tab when input on its own.\n"
                + "Will NOT switch tabs when looking up a particular command.\n\n"
                + "Examples: help tasks | help add | help choices";
        this.dataHelp.add(new HelpFX("help\n\nhelp <command>", description));

        //detail Command.
        description = "Displays all modules and their module credits related to the given degree.\n"
                + "Will also switch to the \"Degree Information\" tab.\n"
                + "Can be used on its own to simply switch tabs. \n\n"
                + "Examples: detail bme | detail Biomedical Engineering";
        this.dataHelp.add(new HelpFX("detail\n\ndetail <degree>", description));

        //swap Command.
        description = "Swaps 2 degrees with the given IDs in your degree choices.\n"
                + "Accepts only integers. \n\n"
                + "Examples: swap 1 2 | swap 01 02";
        this.dataHelp.add(new HelpFX("swap <ID> <ID>", description));

        //delete Command.
        description = "Deletes a task from your task list corresponding to the ID of the task.\n"
                + "Accepts only integers. \n\n"
                + "Examples: delete 1 | delete 02";
        this.dataHelp.add(new HelpFX("delete <ID>", description));

        //remove Command.
        description = "Removes a degree corresponding to the ID from your choice of degrees.\n"
                + "Accepts only integers. \n\n"
                + "Examples: remove 1 | remove 02";
        this.dataHelp.add(new HelpFX("remove <ID>", description));

        //done Command.
        description = "Marks a task corresponding to the ID as done.\n"
                + "Accepts only integers. \n\n"
                + "Examples: done 1 | done 02";
        this.dataHelp.add(new HelpFX("done <ID>", description));

        //sort Command.
        description = "Sorts your tasks according to a given category.\n"
                + "Accepted categories are: priority, date, degree. \n\n"
                + "Examples: sort by priority | sort by date | sort by degree";
        this.dataHelp.add(new HelpFX("sort by <Category>", description));

        //view_employment Command.
        description = "Displays employment rate for a given degree.\n"
                + "Only works with keywords and is case-sensitive.\n"
                + "This produces a bar graph in a separate window. \n\n"
                + "Examples: view_employment BME | view_employment ISE";
        this.dataHelp.add(new HelpFX("view_employment <Degree>", description));

        //compare Command.
        description = "Compares 2 degrees together and displays the differences in modules and their credits.\n"
                + "Will also switch to the \"Degree Differences\" tab.\n"
                + "Can be used on its own to simply switch tabs.\n\n"
                + "Examples: compare Biomedical Engineering Computer Engineering | compare ise ee";
        this.dataHelp.add(new HelpFX("compare\n\ncompare <Degree> <Degree>", description));

        //Todoo Command.
        description = "Adds a Todo task to your list of tasks.\n"
                + "Todo tasks do not require deadlines.\n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Examples: todo Sleep | todo Eat /priority high";
        this.dataHelp.add(new HelpFX("todo <Description>\n\ntodo <Description> /priority\n<priority>", description));

        //event Command.
        description = "Adds an event task to your list of tasks.\n"
                + "Event tasks require deadlines in the following format: DD-MM-YYYY HHmm.\n"
                + "You cannot input start and end times. Only events from adding degrees will have it. \n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Examples: event Sleep /at 01-01-1970 2359 | event Eat /at 01-02-2019 1500 /priority very high";
        this.dataHelp.add(new HelpFX("event <Description> /at \n<DD-MM-YYYY HHmm>\n\n"
                + "event <Description> /at \n<DD-MM-YYYY HHmm> \n/priority <priority>", description));

        //deadline Command.
        description = "Adds a deadline task to your list of tasks.\n"
                + "Deadline tasks require deadlines in the following format: DD-MM-YYYY HHmm.\n"
                + "Optional priorities can be set when adding tasks from: low, normal, high, very high.\n"
                + "This is done by adding /priority <priority> behind the task.\n\n"
                + "Examples: deadline Sleep /by 01-01-1970 2359 | deadline Eat /by 01-02-2019 1500 /priority very high";
        this.dataHelp.add(new HelpFX("deadline <Description> /by \n<DD-MM-YYYY HHmm>\n\n"
                + "deadline <Description> /by \n<DD-MM-YYYY HHmm> \n/priority <priority>", description));

        //cohort_size Command.
        description = "Displays cohort size for a given degree.\n"
                + "Only works with keywords and is case-sensitive.\n"
                + "This produces a bar graph in a separate window. \n\n"
                + "Examples: cohort_size BME | cohort_size ISE";
        this.dataHelp.add(new HelpFX("cohort_size <Degree>", description));

        //find Command.
        description = "Checks your task list and searches for a tasks matching the input string.\n"
                + "This command is case sensitive. \n\n"
                + "Examples: find application | find Sleep";
        this.dataHelp.add(new HelpFX("find <String>", description));

        //snooze Command.
        description = "Changes the deadline of the task corresponding to the input ID.\n"
                + "Format of deadline is DD-MM-YYYY HHmm. \n\n"
                + "Examples: snooze 1 /to 01-01-1970 2030 | snooze 02 /to 11-11-2019 2030";
        this.dataHelp.add(new HelpFX("snooze <ID> /to \n<DD-MM-YYYY HHmm>", description));

        //schedule Command.
        description = "Retrieves and displays all tasks happening on the given date.\n"
                + "Format of date is DD-MM-YYYY. \n\n"
                + "Examples: schedule 01-01-1970 | schedule 18-05-2019";
        this.dataHelp.add(new HelpFX("schedule <DD-MM-YYYY>", description));

        //undo Command.
        description = "Undoes the most recent command.\n"
                + "This only works for commands that modify tasks or choices.";
        this.dataHelp.add(new HelpFX("undo", description));

        //redo Command.
        description = "Redoes the most recent undone command.\n"
                + "This only works for commands that modify tasks or choices.";
        this.dataHelp.add(new HelpFX("redo", description));

        //keywords Command.
        description = "Displays the degrees and their accepted key words and aliases.\n"
                + "Will also switch to the \"Keywords\" tabs.\n"
                + "These key words and aliases are compatible with the \"add\", \"detail\" and \"compare\" command.\n"
                + "Only KEYWORDS are compatible with the \"view_employment\" and \"cohort_size\" command.";
        this.dataHelp.add(new HelpFX("keywords", description));

        helpView.sort();
    }
}

