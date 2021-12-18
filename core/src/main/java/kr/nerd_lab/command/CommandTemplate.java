package kr.nerd_lab.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandTemplate<Result> implements CommandResult<Result> {
    private static String PREFIX_OPERATION = "--";
    private static String PREFIX_OPTION = "--";
    private final Map<String, CommandOption<Result>> operations;
    private ArgumentBinder binder;
    private Throwable error;
    private Result result;

    public CommandTemplate() {
        operations = new HashMap<>();
    }

    public CommandTemplate<Result> operation(String operation, CommandOperation<Result> commandOption) {
        operations.put(operation, (CommandOption<Result>) commandOption.operation(new CommandOption<Result>()));
        return this;
    }

    public CommandTemplate<Result> prefix(String operation, String option) {
        PREFIX_OPERATION = operation;
        PREFIX_OPTION = option;

        return this;
    }


    public CommandTemplate<Result> args(String... args) {
        List<String> argumentList = Arrays.asList(args);

        binder = new ArgumentBinder();

        argumentList.stream()
                .map(String::trim)
                .forEach(binder::bindArguments);
        try {
            this.result = execute();
        } catch (Throwable e) {
            this.error = e;
        }
        return this;
    }

    private Result execute() throws Throwable {
        return binder.execute();
    }

    private void help() {
        operations.forEach((operation, options) -> {
            StringBuilder builder = new StringBuilder("help manual\n");
            builder
                    .append("===============================================\n")
                    .append(PREFIX_OPERATION)
                    .append(operation)
                    .append(" : ")
                    .append(options.getDescription())
                    .append("\n");

            options.getOptionDescriptions()
                    .forEach((option, description)
                            -> builder.append("\t")
                            .append(PREFIX_OPTION)
                            .append(option)
                            .append(" : ")
                            .append(description)
                            .append("\n")
                    );

            System.out.println(builder);
        });
    }

    @Override
    public Result result() throws Throwable {
        if (error != null) {
            throw error;
        }
        return result;
    }

    private class ArgumentBinder {
        private int current = 0;
        private int at;
        private CommandOption<Result> currentCommandOption;

        String operation;
        String option;

        private void bindArguments(String arg) {
            if (current == 0) {
                initOperation(arg);
            } else if (arg.startsWith(PREFIX_OPERATION)) {
                initOption(arg);
            } else {
                initParameter(arg);
            }
            current++;
        }


        private void initOperation(String arg) {
            operation = arg.replaceFirst(PREFIX_OPERATION, "");
            if (!arg.startsWith(PREFIX_OPERATION)) {
                fire(operation);
            }
            if (operations.containsKey(operation)) {
                currentCommandOption = operations.get(operation);
            } else if (operation.equals("help")) {
                CommandTemplate.this.help();
                System.exit(0);
            } else {
                fire(operation);
            }
        }

        private void initOption(String arg) {
            option = arg.replaceFirst(PREFIX_OPTION, "");
            if (currentCommandOption.contains(option)) {
                at = current;
            } else {
                fire(operation, option);
            }
        }

        private void initParameter(String value) {
            if (at == (current - 1)) {
                currentCommandOption.set(option, value);
            } else {
                fire();
            }
        }

        public Result execute() throws Throwable {
            return currentCommandOption.execute();
        }


        //TODO : need to fire with ReasonStatus Enum
        private void fire() {
            fire(null);
        }

        private void fire(String operation) {
            fire(operation, null);
        }

        //TODO : exit with Enum. cause print errorCode List with exitCode
        private void fire(String operation, String option) {
            CommandTemplate.this.help();
            System.exit(-1);
        }
    }
}
