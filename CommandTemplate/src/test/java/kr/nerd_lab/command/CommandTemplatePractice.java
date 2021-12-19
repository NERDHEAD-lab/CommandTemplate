package kr.nerd_lab.command;

public class CommandTemplatePractice {
    public static void main(String[] args) {
        args = "--copy --input a --output b".trim().split(" ");
//                new String[]{
//                "--copy", "--input", "a", "--output", "b"
//        };

        CommandTemplate<String> commandTemplate =
                CommandTemplate.create(String.class)
                        .operation(
                                "copy",
                                commandOption ->
                                        commandOption.description("")
                                                .option("input", "복사 할 대상")
                                                .option("output", "이동 할 위치")
                                                .action(
                                                        options -> {
                                                            StringBuilder builder = new StringBuilder();

                                                            String input = options.get("input");
                                                            String output = options.get("output");

                                                            builder.append("input = ").append(input).append("\n")
                                                                    .append("output = ").append(output);

                                                            return builder.toString();
                                                        })
                        );


        try {
            String result = commandTemplate.args(args).result();
            System.out.println(result);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        commandTemplate.args("--help");
    }
}
