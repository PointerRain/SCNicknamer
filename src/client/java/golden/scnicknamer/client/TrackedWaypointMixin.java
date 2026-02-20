package golden.scnicknamer.client;

import com.mojang.datafixers.util.Either;
import golden.scnicknamer.DisplayMapping;
import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.util.ARGB;
import net.minecraft.world.waypoints.TrackedWaypoint;
import net.minecraft.world.waypoints.Waypoint;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin (TrackedWaypoint.class)
public class TrackedWaypointMixin {
    @Unique
    SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();
    @Shadow
    @Final
    protected Either<UUID, String> identifier;
    @Shadow
    @Final
    private Waypoint.Icon icon;

    @Inject(method = "icon", at = @At("RETURN"), cancellable = true)
    private void onGetConfig(CallbackInfoReturnable<Waypoint.Icon> cir) {
        Waypoint.Icon config = this.icon;

        if (!SCNicknamerClient.isEnabled() || !CONFIG.locatorbar) {
            return;
        }
        UUID uuid = identifier.left().orElse(null);
        if (uuid == null) {
            return;
        }
        DisplayMapping mapping = SCNicknamerClient.getMapping(uuid);
        if (mapping == null || mapping.colour() == null || mapping.colour().isEmpty()) {
            return;
        }
        int k = ARGB.color(255, Integer.parseInt(mapping.colour(), 16));
        config.color = java.util.Optional.of(k);

        cir.setReturnValue(config);
    }
}
