package com.example.veritymod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;

public final class ScaleKeybinds {
    public static final KeyMapping INCREASE = new KeyMapping(
            "key.veritymod.increase",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_UP,
            KeyMapping.Category.GAMEPLAY
    );

    public static final KeyMapping DECREASE = new KeyMapping(
            "key.veritymod.decrease",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_DOWN,
            KeyMapping.Category.GAMEPLAY
    );

    public static final KeyMapping DISABLE = new KeyMapping(
            "key.veritymod.disable",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_PERIOD,
            KeyMapping.Category.GAMEPLAY
    );

    private ScaleKeybinds() {
    }

    public static void init() {
        RegisterKeyMappingsEvent.BUS.addListener(event -> {
            event.register(INCREASE);
            event.register(DECREASE);
            event.register(DISABLE);
        });

        TickEvent.ClientTickEvent.Post.BUS.addListener(event -> onClientTick());
    }

    private static void onClientTick() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) {
            return;
        }

        while (DISABLE.consumeClick()) {
            if (ScaleState.isDisabled()) {
                continue;
            }
            ScaleState.setExempt(mc.player.getUUID());
            ScaleState.disable();
            applyLocal(mc.level);
            ScaleNetwork.sendToServer(ScaleState.getFactor(), true);
        }
        if (ScaleState.isDisabled()) {
            return;
        }

        while (INCREASE.consumeClick()) {
            ScaleState.setExempt(mc.player.getUUID());
            ScaleState.increase();
            applyLocal(mc.level);
            ScaleNetwork.sendToServer(ScaleState.getFactor(), false);
        }
        while (DECREASE.consumeClick()) {
            ScaleState.setExempt(mc.player.getUUID());
            ScaleState.decrease();
            applyLocal(mc.level);
            ScaleNetwork.sendToServer(ScaleState.getFactor(), false);
        }
    }

    private static void applyLocal(ClientLevel level) {
        for (Entity entity : level.entitiesForRendering()) {
            entity.refreshDimensions();
        }
    }
}