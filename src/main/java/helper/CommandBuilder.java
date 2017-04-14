package helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandBuilder {
    protected String command;
    protected LinkedHashMap<String, String> paramsMap = new LinkedHashMap<>();

    public CommandBuilder addParam(String name, String value) {
        paramsMap.put(name, value);

        return this;
    }

    public CommandBuilder setCommand(String command) {
        this.command = command;

        return this;
    }

    public CommandBuilder addParam(String name) {
        paramsMap.put(name, null);

        return this;
    }

    public CommandBuilder addParam(String name, int value) {
        addParam(name, Integer.toString(value));

        return this;
    }

    public String[] build() {
        ArrayList<String> commandParts = new ArrayList<>();

        // Put command at first place
        commandParts.add(command);

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            if (value != null) {
                commandParts.add(name);
                commandParts.add(value);
            } else {
                commandParts.add(name);
            }
        }

        return commandParts.toArray(new String[commandParts.size()]);
    }
}
