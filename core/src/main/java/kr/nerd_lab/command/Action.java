package kr.nerd_lab.command;


@FunctionalInterface
public interface Action<Result> {
    Result run(CommandOption.Options options) throws Throwable;
}
