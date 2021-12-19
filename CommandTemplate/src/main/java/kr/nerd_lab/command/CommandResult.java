package kr.nerd_lab.command;

public interface CommandResult<Result> {
    public Result result() throws Throwable;
}
