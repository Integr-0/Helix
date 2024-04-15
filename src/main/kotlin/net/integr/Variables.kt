package net.integr

import java.awt.Color

class Variables {
    companion object {
        var guiColor: Int = Color(0, 0, 0).rgb
        var guiBack: Int = Color(25, 24, 24).rgb
        val guiDisabled: Int = Color(151, 151, 151).rgb

        var guiColorIsRgb = true
    }
}