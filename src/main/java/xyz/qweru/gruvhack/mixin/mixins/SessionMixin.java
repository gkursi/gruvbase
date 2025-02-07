package xyz.qweru.gruvhack.mixin.mixins;

import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import xyz.qweru.gruvhack.mixin.mixininterface.ISession;

@Mixin(Session.class)
public class SessionMixin implements ISession {
    @Mutable
    @Shadow @Final private String username;

    @Override
    public void gruv$setUser(String name) {
        this.username = name;
    }
}
