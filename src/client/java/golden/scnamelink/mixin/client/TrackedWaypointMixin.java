package golden.scnamelink.mixin.client;

import com.mojang.datafixers.util.Either;
import golden.scnamelink.DisplayMapping;
import golden.scnamelink.SpooncraftNameLinkClient;
import golden.scnamelink.config.SCNameLinkConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.waypoint.TrackedWaypoint;
import net.minecraft.world.waypoint.Waypoint;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin (TrackedWaypoint.class)
public class TrackedWaypointMixin {
    @Unique
    SCNameLinkConfig CONFIG = AutoConfig.getConfigHolder(SCNameLinkConfig.class).getConfig();
    @Shadow
    @Final
    protected Either<UUID, String> source;
    @Shadow
    @Final
    private Waypoint.Config config;

    @Inject (method = "getConfig", at = @At ("RETURN"), cancellable = true)
    private void onGetConfig(CallbackInfoReturnable<Waypoint.Config> cir) {
        Waypoint.Config config = this.config;

        if (!CONFIG.enableMod || !CONFIG.locatorbar) {
            return;
        }
        UUID uuid = source.left().orElse(null);
        if (uuid == null) {
            return;
        }
        DisplayMapping mapping = SpooncraftNameLinkClient.getMapping(uuid, null);
        if (mapping == null || mapping.colour == null || mapping.colour.isEmpty()) {
            return;
        }
        int k = ColorHelper.withAlpha(255, Integer.parseInt(mapping.colour, 16));
        config.color = java.util.Optional.of(k);

        cir.setReturnValue(config);
    }
}
