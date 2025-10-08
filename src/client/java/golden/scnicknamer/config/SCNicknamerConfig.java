package golden.scnicknamer.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * Stores configuration options for the SCNicknamer mod.
 */
@Config (name = "scnicknamer")
public class SCNicknamerConfig implements ConfigData {
    // Whether to enable the mod. Game should be restarted after enabling.
    @ConfigEntry.Gui.Tooltip
    public boolean enableMod = true;
    // The uri of the data api. Leave blank if unsure. Game should be restarted after changing.
    @ConfigEntry.Gui.Tooltip(count = 2)
    public String apiLink = "";

    // Whether to replace names in the tablist using nicknames.
    public boolean replacetablist = false;
    // Whether to colour names using provided colours.
    public boolean colourtablist = true;

    // Whether to replace names in player nametags using nicknames.
    public boolean replacenametag = false;
    // Whether to colour names in using provided colours.
    public boolean colournametag = true;

    // Whether to replace names in the chat using nicknames.
    public boolean replacechat = false;
    // Whether to colour names in using provided colours.
    public boolean colourchat = true;

    // Whether to colour the icons in the locator bar.
    public boolean locatorbar = true;

    public enum WhitelistMode {
        ALL, AUTOWHITELIST, AUTOBLACKLIST, NONE
    }
//    public WhitelistMode whitelistMode = WhitelistMode.AUTOWHITELIST;

    // Whether to use the recommended whitelist.
    @ConfigEntry.Gui.Tooltip
    public boolean useWhitelist = true;
}