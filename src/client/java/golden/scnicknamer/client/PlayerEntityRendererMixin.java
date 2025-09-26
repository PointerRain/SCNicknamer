package golden.scnicknamer.client;

import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import golden.scnicknamer.interfaces.IEntityProvider;

import me.shedaniel.autoconfig.AutoConfig;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin (PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<AvatarlikeEntity extends PlayerLikeEntity & ClientPlayerLikeEntity> {
    @Unique
    SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();


    @ModifyArgs (method = "renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
            at = @At (value = "INVOKE",
                    target = "net/minecraft/client/render/command/OrderedRenderCommandQueue.submitLabel(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Vec3d;ILnet/minecraft/text/Text;ZIDLnet/minecraft/client/render/state/CameraRenderState;)V"))
    protected void renderLabelIfPresent(Args args, @Local (argsOnly = true) PlayerEntityRenderState state) {

        Text text = args.get(3);

        if ((!CONFIG.replacenametag && !CONFIG.colournametag) || !CONFIG.enableMod) {
            return;
        }
        PlayerEntity player = (PlayerEntity) ((IEntityProvider) state).spooncraft_Name_Link$getEntity();

        Text label = SCNicknamerClient.getStyledName(text, player.getUuid(),
                                                     player.getStringifiedName(),
                                                     CONFIG.replacenametag, CONFIG.colournametag);
        args.set(3, label);
    }


    @Inject (method = "updateRenderState*", at = @At("TAIL"))
    public void populateEntityInState(AvatarlikeEntity entity, PlayerEntityRenderState state, float f, CallbackInfo ci) {
        ((IEntityProvider) state).spooncraft_Name_Link$setEntity(entity);
    }
}