package net.integr.commands.suggestors

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.integr.Helix
import java.util.concurrent.CompletableFuture

class PlayerStringSuggestionProvider : SuggestionProvider<FabricClientCommandSource> {
    override fun getSuggestions(context: CommandContext<FabricClientCommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (p in Helix.MC.server!!.playerNames) builder.suggest(p)

        return builder.buildFuture()
    }
}