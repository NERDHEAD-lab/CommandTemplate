package kr.nerd_lab.command;


import java.util.HashMap;
import java.util.Map;

public abstract class Action {
    private Map<String, String> options = null;

    public abstract void run() throws Exception;

    public void _run() {
        try {
            run();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);    //TODO : SystemExist Enum 컨트롤 필요
        }
    }

    protected String option(String option) {
        return options.get(option);
    }

    private void setOptions(HashMap<String, String> options) {
        this.options = options;
    }
}
