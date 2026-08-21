package com.example.veritymod;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public final class ScaleEvents {

    private ScaleEvents() {
    }

    public static void apply(MinecraftServer server, float factor, boolean disabled) {
        ScaleState.setDisabled(disabled);
        ScaleState.setFactor(factor);
        for (ServerLevel level : server.getAllLevels()) {
            for (Entity entity : level.getEntities().getAll()) {
                entity.refreshDimensions();
            }
        }
    }
}