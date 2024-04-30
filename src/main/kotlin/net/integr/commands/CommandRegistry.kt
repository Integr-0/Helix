package net.integr.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.integr.Helix
import net.integr.commands.suggestors.PlayerStringSuggestionProvider
import net.integr.rendering.screens.MenuScreen
import net.integr.utilities.CommandSender
import net.integr.utilities.LogUtils
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.text.Text

class CommandRegistry {
    companion object {
        private const val PASS = 1

        private val commands: MutableList<LiteralArgumentBuilder<FabricClientCommandSource>> = mutableListOf()

        private fun register(value: LiteralArgumentBuilder<FabricClientCommandSource>) {
            commands.add(value)
        }

        private fun reg() {
            register(ClientCommandManager
                .literal("helix")
                .executes {
                    it.source.client.send { it.source.client.setScreen(MenuScreen.INSTANCE) }
                    it.source.sendFeedback(LogUtils.getChatLog("Please remember you can also use the hotkey [${Text.translatable(Helix.openKey!!.boundKeyTranslationKey).string}] to open the ui."))
                    PASS
                }
            )

            register(ClientCommandManager
                .literal("gmc")
                .executes {
                    CommandSender.sendChatCommand("gamemode creative")
                    PASS
                }
                .then(argument("player", StringArgumentType.string())
                    .suggests(PlayerStringSuggestionProvider())
                    .executes {
                        val arg = StringArgumentType.getString(it, "player")
                        CommandSender.sendChatCommand("gamemode creative $arg")
                        PASS
                    }
                )
            )

            register(ClientCommandManager
                .literal("gms")
                .executes {
                    CommandSender.sendChatCommand("gamemode survival")
                    PASS
                }
                .then(argument("player", StringArgumentType.string())
                    .suggests(PlayerStringSuggestionProvider())
                    .executes {
                        val arg = StringArgumentType.getString(it, "player")
                        CommandSender.sendChatCommand("gamemode survival $arg")
                        PASS
                    }
                )
            )

            register(ClientCommandManager
                .literal("gmsp")
                .executes {
                    CommandSender.sendChatCommand("gamemode spectator")
                    PASS
                }
                .then(argument("player", StringArgumentType.string())
                    .suggests(PlayerStringSuggestionProvider())
                    .executes {
                        val arg = StringArgumentType.getString(it, "player")
                        CommandSender.sendChatCommand("gamemode spectator $arg")
                        PASS
                    }
                )
            )

            register(ClientCommandManager
                .literal("gma")
                .executes {
                    CommandSender.sendChatCommand("gamemode adventure")
                    PASS
                }
                .then(argument("player", StringArgumentType.string())
                    .suggests(PlayerStringSuggestionProvider())
                    .executes {
                        val arg = StringArgumentType.getString(it, "player")
                        CommandSender.sendChatCommand("gamemode adventure $arg")
                        PASS
                    }
                )
            )
        }

        fun init() {
            reg()
            ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource>, _: CommandRegistryAccess ->
                for (cmd in commands) {
                    dispatcher.register(cmd)
                }
            }
        }
    }
}