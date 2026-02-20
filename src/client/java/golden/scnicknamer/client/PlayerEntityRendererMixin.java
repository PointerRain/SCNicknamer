package golden.scnicknamer.client;

import com.llamalad7.mixinextras.sugar.Local;
import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import golden.scnicknamer.interfaces.IEntityProvider;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(AvatarRenderer.class)
public abstract class PlayerEntityRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> {
    @Unique
    SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();


    @ModifyArgs(method = "submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;" +
            "Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;" +
            "Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
            at = @At (value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag" +
                            "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;" +
                            "ILnet/minecraft/network/chat/Component;" +
                            "ZIDLnet/minecraft/client/renderer/state/CameraRenderState;)V"))
    protected void submitNameTag(Args args, @Local(argsOnly = true) AvatarRenderState state) {

        Component text = args.get(3);

        if (!SCNicknamerClient.isEnabled() || (!CONFIG.replacenametag && !CONFIG.colournametag)) {
            return;
        }
        Player player = (Player) ((IEntityProvider) state).spooncraft_Name_Link$getEntity();

        Component label = SCNicknamerClient.getStyledName(text, player.getUUID(),
                                                     CONFIG.replacenametag, CONFIG.colournametag);
        args.set(3, label);
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;" +
            "Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
    public void populateEntityInState(AvatarlikeEntity entity, AvatarRenderState state, float f, CallbackInfo ci) {
        ((IEntityProvider) state).spooncraft_Name_Link$setEntity(entity);
    }
}