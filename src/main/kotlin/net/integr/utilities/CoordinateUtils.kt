/*
 * Copyright © 2024 Integr
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.integr.utilities

import net.minecraft.entity.Entity
import net.minecraft.util.math.Box
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

        fun getEntityBox(e: Entity): Box {
            return Box(-(e.boundingBox.lengthX/2), 0.0, -(e.boundingBox.lengthZ/2), (e.boundingBox.lengthX/2), e.boundingBox.lengthY, (e.boundingBox.lengthZ/2))
        }
    }
}