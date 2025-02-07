package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.qweru.gruvhack.mixin.mixininterface.IPlayerInteractEntityC2SPacket;

@Mixin(PlayerInteractEntityC2SPacket.class)
public class PlayerInteractEntityC2SPacketMixin implements IPlayerInteractEntityC2SPacket {
    @Mutable
    @Shadow @Final private int entityId;

    @Override
    public void gruv$setId(int id) {
        this.entityId = id;
    }
}
