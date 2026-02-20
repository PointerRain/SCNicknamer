package golden.scnicknamer.client;

import golden.scnicknamer.DisplayMapping;
import golden.scnicknamer.SCNicknamerClient;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mixin(HoverEvent.EntityTooltipInfo.class)
public abstract class HoverEventMixin {
    @Final
    @Shadow
    public UUID uuid;
    @Final
    @Shadow
    public Optional<Component> name;
    @Final
    @Shadow
    public EntityType<?> type;
    @Shadow
    @Nullable
    private List<Component> linesCache;

    @Inject(method = "getTooltipLines()Ljava/util/List;", at = @At("HEAD"))
    public @Nullable List<Component> asTooltip(CallbackInfoReturnable<List<Component>> cir) {
        if (this.linesCache == null) {
            this.linesCache = new ArrayList<>();
            this.name.ifPresent(this.linesCache::add);
            this.linesCache.add(Component.translatable("gui.entity_tooltip.type",
                    this.type.getDescription()));
            if (this.type == EntityType.PLAYER && this.name.isPresent()) {
                DisplayMapping mapping = SCNicknamerClient.getMapping(this.uuid);
                if (mapping != null && mapping.nickname() != null) {
                    this.linesCache.add(Component.translatable("gui.scnicknamer.hover_nickname",
                                                       mapping.nickname()));
                }
            }
            this.linesCache.add(Component.translationArg(this.uuid));
        }

        return this.linesCache;
    }
}