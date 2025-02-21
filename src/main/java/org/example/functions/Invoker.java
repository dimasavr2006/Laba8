package org.example.functions;

import org.example.commands.*;

import java.util.HashMap;
import java.util.Map;

public class Invoker {

    public Map<String, Command> commands = new HashMap<>();

    public Invoker() {
        commands.put("help", new HelpCommand());
        commands.put("info", new InfoCommand());
        commands.put("show", new ShowCommand());
        commands.put("add", new AddElementCommand());
        commands.put("update_id", new UpdateIDCommand());
        commands.put("remove_by_id", new RemoveByIDCommand());
        commands.put("clear", new ClearCommand());
        commands.put("save", new SaveToFileCommand());
        commands.put("execute_script", new ExecuteScriptFromFileCommand());
        commands.put("exit", new ExitCommand());
        commands.put("remove_first", new RemoveFirstCommand());
        commands.put("add_if_min", new AddIfMinCommand());
        commands.put("sort", new SortCommand());
        commands.put("remove_any_by_mood", new RemoveAnyByMoodCommand());
        commands.put("min_by_soundtrack_name", new MinBySoundtrackNameCommand());
        commands.put("count_greater_than_mood", new CountGreaterThanMoodCommand());

    }
}
