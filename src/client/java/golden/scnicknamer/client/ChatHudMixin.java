package golden.scnicknamer.client;

import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(ChatComponent.class)
public class ChatHudMixin {
    @Unique
    private static final SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();

    @ModifyArgs(method = "addMessage(Lnet/minecraft/network/chat/Component;" +
            "Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
                at = @At(value = "INVOKE",
                        target = "Lnet/minecraft/client/GuiMessage;<init>(ILnet/minecraft/network/chat/Component;" +
                                "Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V"))
    public void onReceivingMessages(Args args) {

        if ((!SCNicknamerClient.isEnabled() || !CONFIG.replacechat && !CONFIG.colourchat)) {
            return;
        }

        Component message = args.get(1);

        args.set(1, SCNicknamerClient.getStyledChat(message, CONFIG.replacechat,
                                                    CONFIG.colourchat));
    }
}
