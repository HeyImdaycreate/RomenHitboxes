package com.example.veritymod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record ScalePacket(float factor, boolean disabled) {

    public static void encode(ScalePacket msg, FriendlyByteBuf buf) {
        buf.writeFloat(msg.factor());
        buf.writeBoolean(msg.disabled());
    }

    public static ScalePacket decode(FriendlyByteBuf buf) {
        return new ScalePacket(buf.readFloat(), buf.readBoolean());
    }

    public static void handle(ScalePacket msg, CustomPayloadEvent.Context ctx) {
        ctx.setPacketHandled(true);
        if (!ctx.isServerSide()) {
            return;
        }
        ServerPlayer sender = ctx.getSender();
        if (sender == null) {
            return;
        }
        ScaleState.setExempt(sender.getUUID());
        ScaleEvents.apply(sender.level().getServer(), msg.factor(), msg.disabled());
    }
}