package kr.nerd_lab.command;

public interface CommandOptionTemplate<Result> {
    CommandOptionTemplate<Result> action(Action<Result> action);

    CommandOptionTemplate<Result> description(String description);

    CommandOptionTemplate<Result> option(String option, String description);
}
