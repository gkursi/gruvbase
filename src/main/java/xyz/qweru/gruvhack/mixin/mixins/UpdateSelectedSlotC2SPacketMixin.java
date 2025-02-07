package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.qweru.gruvhack.mixin.mixininterface.IUpdateSelectedSlotC2SPacket;

@Mixin(UpdateSelectedSlotC2SPacket.class)
public class UpdateSelectedSlotC2SPacketMixin implements IUpdateSelectedSlotC2SPacket {
    @Mutable
    @Shadow @Final private int selectedSlot;

    @Override
    public void gruv$setSlot(int slot) {
        this.selectedSlot = slot;
    }
}
