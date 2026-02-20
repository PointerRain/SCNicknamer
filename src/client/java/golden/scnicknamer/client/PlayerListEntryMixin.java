package golden.scnicknamer.client;

import com.mojang.authlib.GameProfile;
import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class PlayerListEntryMixin {
    @Unique
    SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();

    @Shadow
    @Final
    private GameProfile profile;

    @Shadow
    @Nullable
    private Component tabListDisplayName;

    @Inject(at = @At("RETURN"), method = "getTabListDisplayName", cancellable = true)
    public void replaceDisplayName(CallbackInfoReturnable<Component> cir) {

        if (!SCNicknamerClient.isEnabled() || (!CONFIG.replacetablist && !CONFIG.colourtablist)) {
            return;
        }

        if (tabListDisplayName == null && profile.name() != null) {
            tabListDisplayName = Component.nullToEmpty(profile.name());
        }

        Component label = SCNicknamerClient.getStyledName(tabListDisplayName, profile.id(),
                                                     CONFIG.replacetablist, CONFIG.colourtablist);
        cir.setReturnValue(label);
    }
}