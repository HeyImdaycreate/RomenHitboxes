package com.example.veritymod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public final class ScaleHud {
    private static final int PINK = 0xFFFF69B4;

    private ScaleHud() {
    }

    public static void render(GuiGraphics graphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null || ScaleState.isDisabled()) {
            return;
        }
        graphics.drawString(mc.font, "RomenHitboxes", 2, 2, PINK);
    }
}