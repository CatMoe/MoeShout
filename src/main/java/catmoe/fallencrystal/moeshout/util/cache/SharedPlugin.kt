package catmoe.fallencrystal.moeshout.util.cache

import net.md_5.bungee.api.plugin.Plugin

object SharedPlugin {
    var plugin : Plugin? = null

    fun setPlugin(target: Plugin) {
        plugin = target
    }

    fun getPlugin(): Plugin? { return plugin }


}