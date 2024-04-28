package net.integr.modules.impl

import net.integr.Helix
import net.integr.event.AttackEvent
import net.integr.event.BlockBreakEvent
import net.integr.event.ItemUseEvent
import net.integr.event.PreTickEvent
import net.integr.event.SendPacketEvent
import net.integr.event.SwingHandEvent
import net.integr.eventsystem.EventListen
import net.integr.modules.filters.Filter
import net.integr.modules.management.Module
import net.integr.modules.management.settings.impl.BooleanSetting
import net.integr.modules.management.settings.impl.CyclerSetting
import net.integr.utilities.LogUtils
import net.minecraft.block.Blocks
import net.minecraft.entity.Entity
import net.minecraft.entity.decoration.EndCrystalEntity
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.*
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.EntityHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.Vec3d


class CrystalOptimizerModule : Module("Optimizer", "Increases crystal speed by removing crystals clientside after they were hit", "optimizer", listOf(Filter.Tweak, Filter.Combat)) {
    init {
        settings.add(BooleanSetting("Mine-Block", "Blocks mining obsidian while holding a crystal in experimental mode", "mineBlock"))
    }

    @EventListen
    fun onPacket(event: SendPacketEvent) {
        val packet = event.packet

        if (packet is PlayerInteractEntityC2SPacket) {
            val hitResult = Helix.MC.crosshairTarget
            if (hitResult != null) {
                if (hitResult.type == HitResult.Type.ENTITY) {
                    if ((hitResult as EntityHitResult).entity is EndCrystalEntity) {
                        packet.handle(EntityHandler())
                    }
                }
            }
        }
    }

    @EventListen
    fun onAttack(event: AttackEvent) {
        if (settings.getById<BooleanSetting>("mineBlock")!!.isEnabled()) {
            val hitResult = Helix.MC.crosshairTarget
            if (hitResult!!.type == HitResult.Type.BLOCK) {
                val block = Helix.MC.world!!.getBlockState((hitResult as BlockHitResult).blockPos).block

                if (block == Blocks.OBSIDIAN || block == Blocks.BEDROCK) {
                    if (Helix.MC.player!!.inventory.mainHandStack.item == Items.END_CRYSTAL) Helix.MC.options.attackKey.isPressed = false
                }
            }
        }
    }

    class EntityHandler : PlayerInteractEntityC2SPacket.Handler {
        override fun interact(hand: Hand) {

        }
        override fun interactAt(hand: Hand, pos: Vec3d) {

        }

        override fun attack() {
            val hitResult = Helix.MC.crosshairTarget
            if (hitResult != null) {
                if (hitResult.type == HitResult.Type.ENTITY) {
                    val entityHitResult = hitResult as EntityHitResult
                    val entity = entityHitResult.entity

                    if (entity is EndCrystalEntity) {
                        val weakness = Helix.MC.player!!.getStatusEffect(StatusEffects.WEAKNESS)
                        val strength = Helix.MC.player!!.getStatusEffect(StatusEffects.STRENGTH)

                        if (weakness != null && (strength == null || strength.amplifier <= weakness.amplifier) && !isTool(Helix.MC.player!!.mainHandStack)) {
                            return
                        }

                        entity.kill()
                        entity.setRemoved(Entity.RemovalReason.KILLED)
                        entity.onRemoved()
                    }
                }

            }
        }

        private fun isTool(itemStack: ItemStack): Boolean {
            if (itemStack.item is ToolItem && itemStack.item !is HoeItem) {
                val material = (itemStack.item as ToolItem).material
                return material == ToolMaterials.DIAMOND || material == ToolMaterials.NETHERITE
            } else {
                return false
            }
        }
    }
}