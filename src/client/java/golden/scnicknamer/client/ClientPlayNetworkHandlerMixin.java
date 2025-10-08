package golden.scnicknamer.client;

import golden.scnicknamer.NameLinkAPI;
import golden.scnicknamer.SCNicknamerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mixin (ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    // The mod ID as used in logging
    @Unique
    private static final String MOD_ID = "scnicknamer";

    // Logger for outputting information to the console and log files
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Shadow
    public abstract ServerInfo getServerInfo();

    @Inject(method = "onGameJoin(Lnet/minecraft/network/packet/s2c/play/GameJoinS2CPacket;)V", at = @At("TAIL"))
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        LOGGER.info("Game joined, current NameLinkAPI status: " + NameLinkAPI.getStatus());
        SCNicknamerClient.setServer(getServerInfo() == null ? null : getServerInfo().address);
        LOGGER.info("Is server whitelisted? " + SCNicknamerClient.isEnabled());
        ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
        if (chatHud != null && !NameLinkAPI.getStatus().equals("Success") && !NameLinkAPI.getStatus().equals("Disabled")) {
            chatHud.addMessage(SCNicknamerClient.getStatusString());
        }
    }
}