package kr.nerd_lab.command;

public interface CommandOperation<Result> {
    CommandOptionTemplate<Result> operation(CommandOptionTemplate<Result> commandOption);
}
