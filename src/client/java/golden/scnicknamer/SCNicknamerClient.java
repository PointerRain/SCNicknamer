package golden.scnicknamer;

import golden.scnicknamer.config.SCNicknamerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Client-side mod initializer for the mod "Spooncraft Name Link".
 */
public class SCNicknamerClient implements ClientModInitializer {

    // The mod ID as used in logging
    private static final String MOD_ID = "scnicknamer";

    // Logger for outputting information to the console and log files
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SCNicknamerConfig config;

    // List of mappings for replacement and optional color changes
    private static HashMap<UUID, DisplayMapping> mappings = new HashMap<>();

    private static List<String> whitelist = new ArrayList<>();
    private static List<String> blacklist = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();

    private static String server;

    /**
     * Retrieves a mapping matching either the UUID or the name of the player.
     *
     * @param uuid The {@code UUID} of the player
     * @return The {@code DisplayMapping} object if found, otherwise null
     */
    public static DisplayMapping getMapping(UUID uuid) {
        return mappings.get(uuid);
    }

    /**
     * Applies the mapping to a given message. It optionally replaces the Minecraft name with
     * the nickname and applies the colour styling.
     *
     * @param message       The original in-game message or name
     * @param mapping       The {@code DisplayMapping} object containing the name and color mapping
     *                      details
     * @param replaceName   Whether to replace the name with the name defined in the mapping
     * @param replaceColour Whether to replace the colour with the colour defined in the mapping
     * @return A new MutableText object with the mapping applied (replacements and color changes)
     */
    static MutableText applyMapping(Text message, DisplayMapping mapping,
                                    boolean replaceName, boolean replaceColour) {
        if (message == null || message.getString().isEmpty() || mapping == null) {
            return (MutableText) message;
        }

        MutableText outputMessage = Text.empty();
        message.visit((style, text) -> {
            String replacedText = text;
            Style replacedStyle = style;

            // Apply the mapping
            if (replacedText.contains(mapping.mc_name())) {
                // Replace the string
                if (mapping.nickname() != null && replaceName) {
                    replacedText = replacedText.replace(mapping.mc_name(), mapping.nickname());
                }
                // Apply color if specified
                if (mapping.colour() != null && replaceColour) {
                    replacedStyle = replacedStyle.withColor(Integer.parseInt(mapping.colour(), 16));
                }
            }

            // Create new MutableText with the display string and style
            MutableText newText = (MutableText) Text.of(replacedText);
            newText.setStyle(replacedStyle);
            outputMessage.append(newText);

            return Optional.empty();  // Continue visiting
        }, Style.EMPTY);

        return outputMessage;
    }

    /**
     * Retrieves and applies the correct name mapping (if any) for a given Minecraft username or
     * UUID.
     * Checks the mappings list to see if the provided displayName or uuid has a corresponding
     * mapping, and if found, applies it by optionally altering the name and color.
     *
     * @param displayName   The original in-game name to be displayed
     * @param uuid          The UUID of the Minecraft player
     * @param replaceName   Whether to replace the name with the name defined in the mapping
     * @param replaceColour Whether to replace the colour with the colour defined in the mapping
     * @return A Text object containing the potentially modified name with appropriate styling
     */
    public static Text getStyledName(Text displayName, UUID uuid, boolean replaceName,
                                     boolean replaceColour) {
        DisplayMapping mapping = getMapping(uuid);
        return (mapping != null)
                ? applyMapping(displayName, mapping, replaceName, replaceColour)
                : displayName;
    }

    /**
     * Styles the chat message by replacing the name and colour if specified.
     * Only styles the name if the text has a {@code HoverEvent} component.
     *
     * @param message       The original in-game message or name
     * @param replaceName   Whether to replace the name with the name defined in the mapping
     * @param replaceColour Whether to replace the colour with the colour defined in the mapping
     * @return A new MutableText object with the mapping applied (replacements and color changes)
     */
    public static Text getStyledChat(Text message, boolean replaceName, boolean replaceColour) {
        if (message == null || message.getString().isEmpty()) {
            return Text.empty();
        }

        MutableText outputMessage = Text.empty();
        message.visit((style, text) -> {
            MutableText newText = Text.literal(text).setStyle(style);

            HoverEvent event = style.getHoverEvent();
            if (event != null && event.getAction() == HoverEvent.Action.SHOW_ENTITY) {
                HoverEvent.EntityContent entity = ((HoverEvent.ShowEntity) event).entity();
                newText = (MutableText) getStyledName(newText, entity.uuid,
                                                      replaceName, replaceColour);
                newText.setStyle(newText.getStyle().withHoverEvent(event));
            }

            outputMessage.append(newText);
            return Optional.empty();
        }, Style.EMPTY);

        return outputMessage;
    }

    /**
     * Retrieves the mappings from the specified source URL or the default URL if none is provided.
     * If the mod is disabled in the configuration, it disables the mod and logs a warning.
     *
     * @param source The URL from which to fetch the mappings. If null or empty, the default URL is
     *               used.
     * @return The number of mappings retrieved.
     */
    public static int getData(String source) {
        String s = (source == null || source.isEmpty())
                ? "https://gwaff.uqcloud.net/scnicknamer/"
                : source;
        ServerResponse data = NameLinkAPI.getData(s);
        mappings = data.mappings() != null ? data.mappings() : new HashMap<>();
        whitelist = data.whitelist() != null ? data.whitelist() : new ArrayList<>();
        blacklist = data.blacklist() != null ? data.blacklist() : new ArrayList<>();
        messages = data.messages() != null ? data.messages() : new ArrayList<>();
        return mappings.size();
    }

    /**
     * Retrieves a Text object detailing the status of the mod.
     *
     * @return A Text object containing the status of the mod
     */
    public static Text getStatusString() {
        return switch (NameLinkAPI.getStatus()) {
            case "Success" ->
                    Text.translatable("text.scnicknamer.status.success").formatted(Formatting.WHITE);
            case "Working" ->
                    Text.translatable("text.scnicknamer.status.working").formatted(Formatting.YELLOW);
            case "Fallback" ->
                    Text.translatable("text.scnicknamer.status.fallback").formatted(Formatting.RED);
            case "Failure" ->
                    Text.translatable("text.scnicknamer.status.failure").formatted(Formatting.RED
                            , Formatting.BOLD);
            default -> Text.of(NameLinkAPI.getStatus());
        };
    }

    /**
     * Sets the current server address.
     * @param currentServer The address of the current server or null/empty if not connected to a server.
     */
    public static void setServer(String currentServer) {
        server = currentServer;
    }

    /**
     * Checks if the mod is enabled based on the configuration and server lists.
     *
     * @return {@code true} if the mod is enabled for the given server, {@code false} otherwise.
     */
    public static boolean isEnabled() {
        if (!config.enableMod) {
            return false;
        }
        if (server == null || server.isEmpty()) {
            return config.enableOnSingleplayer;
        }
        return switch (config.whitelistMode) {
            case ALL -> true;
            case NONE -> false;
            case AUTOWHITELIST -> whitelist.contains(server);
            case AUTOBLACKLIST -> !blacklist.contains(server);
        };
    }

    @Override
    public void onInitializeClient() {
        AutoConfig.register(SCNicknamerConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();

        final int count = getData(config.apiLink);
        if (count > 0) {
            LOGGER.info("{} initialised with {} mappings", MOD_ID, mappings.size());
        } else {
            LOGGER.warn("{} initialised with NO mappings found", MOD_ID);
        }

        if (!config.enableMod) {
            NameLinkAPI.disableMod();
            LOGGER.warn("Mod disabled.");
            return;
        }
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> CommandManager.register(dispatcher));
    }
}
