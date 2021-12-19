package kr.nerd_lab.command;

import java.util.HashMap;
import java.util.Map;

public class CommandOption<Result> implements CommandExecutor, CommandOptionTemplate<Result> {
    private String description;
    private final Options options = new Options();
    private final Map<String, String> optionDescriptions = new HashMap<>();
    private Action<Result> action;

    protected CommandOption() {

    }

    public boolean contains(String option) {
        return options.containsKey(option);
    }

    public void set(String option, String value) {
        options.put(option, value);
    }

    @Override
    public CommandOption<Result> action(Action<Result> action) {
        initAction(action);
        return this;
    }

    private void initAction(Action<Result> action) {
        this.action = action;
    }

    public Result execute() throws Throwable {
        return action.run(options);
    }

    @Override
    public String get(String option) {
        return options.get(option);
    }

    public CommandOption<Result> option(String option, String description) {
        options.put(option, null);
        optionDescriptions.put(option, description);

        return this;
    }

    public CommandOption<Result> description(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getOptionDescriptions() {
        return optionDescriptions;
    }

    static class Options {
        private final HashMap<String, String> options = new HashMap<>();

        public String get(String option) {
            return getAsString(option);
        }

        public String getAsString(String option) {
            return options.get(option);
        }

        private boolean containsKey(String option) {
            return options.containsKey(option);
        }

        private void put(String option, String value) {
            options.put(option, value);
        }
    }
}
