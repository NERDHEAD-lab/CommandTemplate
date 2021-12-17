package kr.nerd_lab.command;

public interface CommandOptionTemplate {
    CommandOptionTemplate action(Action action);
    CommandOptionTemplate description(String description);
    CommandOptionTemplate option(String option, String description);
}
