package net.integr.utilities

import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d

class CoordinateUtils {
    companion object {
        fun getLowestX(blocks: ArrayList<Vec3d>): Double {
            var lowestX = 999999999.0
            for (v in blocks) {
                if (v.getX() < lowestX) {
                    lowestX = v.getX()
                }
            }
            return lowestX
        }

        fun getLowestY(blocks: ArrayList<Vec3d>): Double {
            var lowestY = 999999999.0
            for (v in blocks) {
                if (v.getY() < lowestY) {
                    lowestY = v.getY()
                }
            }
            return lowestY
        }

        fun getLowestZ(blocks: ArrayList<Vec3d>): Double {
            var lowestZ = 999999999.0
            for (v in blocks) {
                if (v.getZ() < lowestZ) {
                    lowestZ = v.getZ()
                }
            }
            return lowestZ
        }

        fun getHighestX(blocks: ArrayList<Vec3d>): Double {
            var maxX = -999999999.0
            for (v in blocks) {
                if (v.getX() > maxX) {
                    maxX = v.getX()
                }
            }
            return maxX
        }

        fun getHighestY(blocks: ArrayList<Vec3d>): Double {
            var maxY = -999999999.0
            for (v in blocks) {
                if (v.getY() > maxY) {
                    maxY = v.getY()
                }
            }
            return maxY
        }

        fun getHighestZ(blocks: ArrayList<Vec3d>): Double {
            var maxZ = -999999999.0
            for (v in blocks) {
                if (v.getZ() > maxZ) {
                    maxZ = v.getZ()
                }
            }
            return maxZ
        }

        fun getLerpedEntityPos(e: Entity, partialTicks: Float): Vec3d {
            if (e.isRemoved) return e.pos

            val x = MathHelper.lerp(partialTicks.toDouble(), e.lastRenderX, e.x)
            val y = MathHelper.lerp(partialTicks.toDouble(), e.lastRenderY, e.y)
            val z = MathHelper.lerp(partialTicks.toDouble(), e.lastRenderZ, e.z)
            return Vec3d(x, y, z)
        }
    }
}