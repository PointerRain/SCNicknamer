package golden.scnicknamer.client;

import golden.scnicknamer.interfaces.IEntityProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityRenderState.class)
public class EntityRendererStateMixin implements IEntityProvider {

    @Unique
    private Entity storedEntity;

    @Override
    public Entity spooncraft_Name_Link$getEntity() {
        return this.storedEntity;
    }

    @Override
    public void spooncraft_Name_Link$setEntity(Entity entity) {
        this.storedEntity = entity;
    }
}
