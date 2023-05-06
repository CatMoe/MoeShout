package catmoe.fallencrystal.moeshout

import catmoe.fallencrystal.moeshout.util.cache.SharedPlugin
import net.md_5.bungee.api.plugin.Plugin

class Main : Plugin() {

    override fun onEnable() {
        SharedPlugin.setPlugin(this)
    }


    override fun onDisable() {

    }
}