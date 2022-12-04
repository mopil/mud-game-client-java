package core;

import dto.RequestDto;

public class CommandProcessor {
    private static final CommandProcessor commandProcessor = new CommandProcessor();
    private CommandProcessor() {}
    public static CommandProcessor getInstance() {
        return commandProcessor;
    }

    public RequestDto process(String cmd) {
        String[] cmdTokens = cmd.split(" ");
        String prefix = cmdTokens[0];
        if (prefix.equals("move")) {
            String data = cmdTokens[1] + "," + cmdTokens[2];
            return new RequestDto("move", data);
        } else if (prefix.equals("attack")) {

        }
        return null;
    }
}
