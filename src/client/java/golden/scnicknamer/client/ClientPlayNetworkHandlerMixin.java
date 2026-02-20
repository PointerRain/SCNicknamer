package golden.scnicknamer.client;

import golden.scnicknamer.NameLinkAPI;
import golden.scnicknamer.SCNicknamerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin {

    // The mod ID as used in logging
    @Unique
    private static final String MOD_ID = "scnicknamer";

    // Logger for outputting information to the console and log files
    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Shadow
    public abstract ServerData getServerData();

    @Inject(method = "handleLogin(Lnet/minecraft/network/protocol/game/ClientboundLoginPacket;)V", at = @At("TAIL"))
    public void onGameJoin(ClientboundLoginPacket packet, CallbackInfo ci) {
        LOGGER.info("Game joined, current NameLinkAPI status: {}", NameLinkAPI.getStatus());
        SCNicknamerClient.setServer(getServerData() == null ? null : getServerData().ip);
        LOGGER.info("Is server whitelisted? {}", SCNicknamerClient.isEnabled());
        ChatComponent chatHud = Minecraft.getInstance().gui.getChat();
        if (NameLinkAPI.getStatus() != NameLinkAPI.FetchStatus.SUCCESS && NameLinkAPI.getStatus() != NameLinkAPI.FetchStatus.DISABLED) {
            chatHud.addMessage(SCNicknamerClient.getStatusString());
        }
    }
}