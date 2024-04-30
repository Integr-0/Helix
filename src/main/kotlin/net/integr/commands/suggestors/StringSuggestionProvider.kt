package net.integr.commands.suggestors

import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.suggestion.SuggestionProvider
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import java.util.concurrent.CompletableFuture

class StringSuggestionProvider(private val suggestions: List<String>) : SuggestionProvider<FabricClientCommandSource> {
    override fun getSuggestions(context: CommandContext<FabricClientCommandSource>, builder: SuggestionsBuilder): CompletableFuture<Suggestions> {
        for (s in suggestions) {
            builder.suggest(s)
        }

        return builder.buildFuture()
    }
}

