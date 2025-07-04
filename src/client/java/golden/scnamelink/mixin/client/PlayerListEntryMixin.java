package golden.scnamelink.mixin.client;

import golden.scnamelink.SpooncraftNameLinkClient;
import golden.scnamelink.config.SCNameLinkConfig;

import me.shedaniel.autoconfig.AutoConfig;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (PlayerListEntry.class)
public abstract class PlayerListEntryMixin {
    @Unique
    SCNameLinkConfig CONFIG = AutoConfig.getConfigHolder(SCNameLinkConfig.class).getConfig();

    @Shadow
    @Final
    private GameProfile profile;

    @Shadow
    @Nullable
    private Text displayName;

    @Inject (at = @At ("RETURN"), method = "getDisplayName", cancellable = true)
    public void replaceDisplayName(CallbackInfoReturnable<Text> cir) {

        if ((!CONFIG.replacetablist && !CONFIG.colourtablist) || !CONFIG.enableMod) {
            return;
        }

        if (displayName == null && profile.getName() != null) {
            displayName = Text.literal(profile.getName());
        }

        Text label = SpooncraftNameLinkClient.getStyledName(displayName, profile.getId(),
                                                            profile.getName(),
                                                            CONFIG.replacetablist, CONFIG.colourtablist);
        cir.setReturnValue(label);
    }
}