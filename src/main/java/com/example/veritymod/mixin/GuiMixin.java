package com.example.veritymod.mixin;

import com.example.veritymod.ScaleHud;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Inject(method = "render", at = @At("RETURN"), remap = false)
    private void veritymod$renderHud(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ScaleHud.render(graphics);
    }
}