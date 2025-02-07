package xyz.qweru.gruvhack.mixin.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(value = CommandDispatcher.class, remap = false)
public class CommandDispatcherMixin {

    @ModifyReturnValue(method = "getCompletionSuggestions(Lcom/mojang/brigadier/ParseResults;)Ljava/util/concurrent/CompletableFuture;", at = @At("RETURN"))
    CompletableFuture<Suggestions> getSuggest(CompletableFuture<Suggestions> original) {
        Suggestions suggestions = new Suggestions(StringRange.between(0, 255), List.of(new Suggestion(StringRange.between(0, 255), "hi")));
        return CompletableFuture.completedFuture(suggestions);
    }

}
