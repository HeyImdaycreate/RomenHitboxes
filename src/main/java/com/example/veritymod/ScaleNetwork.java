package com.example.veritymod;

import net.minecraft.resources.Identifier;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public final class ScaleNetwork {
    private static final SimpleChannel CHANNEL = ChannelBuilder
            .named(Identifier.fromNamespaceAndPath(VerityMod.MODID, "main"))
            .networkProtocolVersion(1)
            .simpleChannel();

    private ScaleNetwork() {
    }

    public static void init() {
        CHANNEL.messageBuilder(ScalePacket.class, 0)
                .encoder(ScalePacket::encode)
                .decoder(ScalePacket::decode)
                .consumerMainThread(ScalePacket::handle)
                .add();
    }

    public static void sendToServer(float factor, boolean disabled) {
        CHANNEL.send(new ScalePacket(factor, disabled), PacketDistributor.SERVER.noArg());
    }
}