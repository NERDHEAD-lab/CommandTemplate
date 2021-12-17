package kr.nerd_lab.command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CommandOption implements CommandExecutor, CommandOptionTemplate {
    private String description;
    private final HashMap<String, String> options = new HashMap<>();
    private final Map<String, String> optionDescriptions = new HashMap<>();
    private Action action;

    private CommandOption() {

    }

    public static CommandOptionTemplate create() {
        return new CommandOption();
    }

    public boolean contains(String option) {
        return options.containsKey(option);
    }

    public void set(String option, String value) {
        options.put(option, value);
    }

    public CommandOption action(Action action) {
        initAction(action);
        return this;
    }

    private void initAction(Action action) {
        try {
            Method commandExecutorField = Action.class.getDeclaredMethod("setOptions", HashMap.class);
            commandExecutorField.setAccessible(true);
            commandExecutorField.invoke( action, options);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        this.action = action;
    }

    public void execute() {
        action._run();
    }

    @Override
    public String get(String option) {
        return options.get(option);
    }

    public CommandOption option(String option, String description) {
        options.put(option, null);
        optionDescriptions.put(option, description);

        return this;
    }

    public CommandOption description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getOptionDescriptions() {
        return optionDescriptions;
    }
}
