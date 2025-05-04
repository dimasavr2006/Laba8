package functions;

import commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dimasavr
 */

public class Invoker {

    /**
     * Класс Invoker
     * В нём хранятся все доступные команды
     */

    public Map<String, Command> commands = new HashMap<String, Command>();

    public static Map<String, Command> staticCommands = new HashMap<>();

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
        commands.put("read", new ReadCommand());
    }

    public static void adder(){
        staticCommands.put("help", new HelpCommand());
        staticCommands.put("info", new InfoCommand());
        staticCommands.put("show", new ShowCommand());
        staticCommands.put("add", new AddElementCommand());
        staticCommands.put("update_id", new UpdateIDCommand());
        staticCommands.put("remove_by_id", new RemoveByIDCommand());
        staticCommands.put("clear", new ClearCommand());
        staticCommands.put("save", new SaveToFileCommand());
        staticCommands.put("execute_script", new ExecuteScriptFromFileCommand());
        staticCommands.put("exit", new ExitCommand());
        staticCommands.put("remove_first", new RemoveFirstCommand());
        staticCommands.put("add_if_min", new AddIfMinCommand());
        staticCommands.put("sort", new SortCommand());
        staticCommands.put("remove_any_by_mood", new RemoveAnyByMoodCommand());
        staticCommands.put("min_by_soundtrack_name", new MinBySoundtrackNameCommand());
        staticCommands.put("count_greater_than_mood", new CountGreaterThanMoodCommand());
        staticCommands.put("read", new ReadCommand());
        staticCommands.put("readEnv", new ReadEnvCommand());
    }
}
