package com.example.veritymod.mixin;

import com.example.veritymod.ScaleState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDimensionsMixin {

    @Inject(method = "makeBoundingBox", at = @At("RETURN"), cancellable = true, remap = false)
    private void veritymod$scaleBoundingBox(CallbackInfoReturnable<AABB> cir) {
        float factor = ScaleState.getFactor();
        if (ScaleState.isDisabled() || factor == 1.0f) {
            return;
        }
        Entity self = (Entity) (Object) this;
        if (ScaleState.isExempt(self.getUUID())) {
            return;
        }
        AABB box = cir.getReturnValue();
        double xz = (box.getXsize() * factor - box.getXsize()) / 2.0;
        double y = box.getYsize() * factor - box.getYsize();
        cir.setReturnValue(box.inflate(xz, 0.0, xz).expandTowards(0.0, y, 0.0));
    }
}