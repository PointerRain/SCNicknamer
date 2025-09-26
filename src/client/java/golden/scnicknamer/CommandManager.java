package golden.scnicknamer;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class CommandManager {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("scnicknamer")
//            .then(ClientCommandManager.literal("reload")
//                .executes(context -> {
//                    SCNicknamerClient.getMappings(null);
//                    context.getSource().sendFeedback(Text.literal("Reloaded SCNicknamer mappings"));
//                    return 1;
//                }))
            .then(ClientCommandManager.literal("load")
                .then(ClientCommandManager.argument("source", StringArgumentType.greedyString())
                    .executes(context -> {
                        String source = StringArgumentType.getString(context, "source");
                        SCNicknamerClient.getMappings(source);
                        context.getSource().sendFeedback(SCNicknamerClient.getStatusString());
                        return 1;
                    }))));
    }
}