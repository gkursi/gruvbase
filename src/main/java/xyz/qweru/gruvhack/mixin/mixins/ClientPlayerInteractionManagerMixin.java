package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.listener.ServerPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.qweru.gruvhack.event.Events;
import xyz.qweru.gruvhack.event.impl.BlockEvent;
import xyz.qweru.gruvhack.mixin.mixininterface.IClientPlayerInteractionManager;

import static xyz.qweru.gruvhack.Gruvhack.mc;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin implements IClientPlayerInteractionManager {

    @Shadow protected abstract void sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator);

    @Inject(method = "attackBlock", at = @At("HEAD"), cancellable = true)
    void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if(Events.BLOCK_ATTACK.call(new BlockEvent(pos, mc.world.getBlockState(pos), direction)).isCancelled()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Override
    public void gruv$sendSequencedPacket(ClientWorld world, SequencedPacketCreator packetCreator) {
        this.sendSequencedPacket(world, packetCreator);
    }

    @Override
    public void gruv$sendSequencedPacket(Packet<ServerPlayPacketListener> packet) {
        this.sendSequencedPacket(mc.world, sequence -> {
            return packet;
        });
    }
}
