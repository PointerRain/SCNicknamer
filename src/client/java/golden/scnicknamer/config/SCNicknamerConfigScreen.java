package golden.scnicknamer.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import static golden.scnicknamer.NameLinkAPI.LOGGER;

public class SCNicknamerConfigScreen {
    public static Screen getConfigScreen(Screen parent) {

        SCNicknamerConfig virtualConfig = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.scnicknamer.title"));

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("config.scnicknamer.config.general"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        builder.setSavingRunnable(() -> {
            AutoConfig.getConfigHolder(SCNicknamerConfig.class).save();
            SCNicknamerConfig conf = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();
            LOGGER.info(conf.toString());
        });

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option.enableMod"), virtualConfig.enableMod)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.scnicknamer.option.enableMod.tooltip"))
                .setSaveConsumer((b) -> virtualConfig.enableMod = b)
                .build());

        general.addEntry(entryBuilder.startStrField(Component.translatable("config.scnicknamer.option.apiLink"),
                        virtualConfig.apiLink)
                .setDefaultValue("")
                .setTooltip(Component.translatable("config.scnicknamer.option.apiLink.tooltip0"),
                        Component.translatable("config.scnicknamer.option.apiLink.tooltip1"))
                .setSaveConsumer((str) -> virtualConfig.apiLink = str)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".replacetablist"), virtualConfig.replacetablist)
                .setDefaultValue(false)
                .setSaveConsumer((b) -> virtualConfig.replacetablist = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".colourtablist"), virtualConfig.colourtablist)
                .setDefaultValue(true)
                .setSaveConsumer((b) -> virtualConfig.colourtablist = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".replacenametag"), virtualConfig.replacenametag)
                .setDefaultValue(false)
                .setSaveConsumer((b) -> virtualConfig.replacenametag = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".colournametag"), virtualConfig.colournametag)
                .setDefaultValue(true)
                .setSaveConsumer((b) -> virtualConfig.colournametag = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".replacechat"), virtualConfig.replacechat)
                .setDefaultValue(false)
                .setSaveConsumer((b) -> virtualConfig.replacechat = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".colourchat"), virtualConfig.colourchat)
                .setDefaultValue(true)
                .setSaveConsumer((b) -> virtualConfig.colourchat = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".locatorbar"), virtualConfig.locatorbar)
                .setDefaultValue(true)
                .setSaveConsumer((b) -> virtualConfig.locatorbar = b)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Component.translatable("config.scnicknamer.option" +
                        ".useWhitelist"), virtualConfig.useWhitelist)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.scnicknamer.option.useWhitelist.tooltip"))
                .setSaveConsumer((b) -> virtualConfig.useWhitelist = b)
                .build());

        return builder.build();
    }
}
