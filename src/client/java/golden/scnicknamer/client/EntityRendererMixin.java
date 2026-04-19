package golden.scnicknamer.client;

import com.llamalad7.mixinextras.sugar.Local;
import golden.scnicknamer.SCNicknamerClient;
import golden.scnicknamer.config.SCNicknamerConfig;
import golden.scnicknamer.interfaces.IEntityProvider;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {
    @Unique
    SCNicknamerConfig CONFIG = AutoConfig.getConfigHolder(SCNicknamerConfig.class).getConfig();


    @ModifyArgs(method = "submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;" +
            "Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;" +
            "Lnet/minecraft/client/renderer/state/level/CameraRenderState;I)V",
            at = @At (value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitNameTag" +
                            "(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;" +
                            "ILnet/minecraft/network/chat/Component;" +
                            "ZIDLnet/minecraft/client/renderer/state/level/CameraRenderState;)V"))
    protected void submitNameTag(Args args, @Local(argsOnly = true) EntityRenderState state) {

        Component text = args.get(3);

        if (!SCNicknamerClient.isEnabled() || (!CONFIG.replacenametag && !CONFIG.colournametag)) {
            return;
        }
        Entity entity = ((IEntityProvider) state).spooncraft_Name_Link$getEntity();
//        if (!(entity instanceof Player player)) {
//            return;
//        }

        Component label = SCNicknamerClient.getStyledName(text, entity.getUUID(),
                                                     CONFIG.replacenametag, CONFIG.colournametag);
        args.set(3, label);
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Entity;" +
            "Lnet/minecraft/client/renderer/entity/state/EntityRenderState;F)V",
            at = @At("TAIL"))
    public void populateEntityInState(Entity entity, EntityRenderState state, float f, CallbackInfo ci) {
        ((IEntityProvider) state).spooncraft_Name_Link$setEntity(entity);
    }
}