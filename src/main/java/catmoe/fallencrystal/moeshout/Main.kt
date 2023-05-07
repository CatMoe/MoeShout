package catmoe.fallencrystal.moeshout

import catmoe.fallencrystal.moeshout.command.CommandManager
import catmoe.fallencrystal.moeshout.util.cache.SharedPlugin
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class Main : Plugin() {

    val proxy = ProxyServer.getInstance()

    override fun onEnable() {
        SharedPlugin.setPlugin(this)
        proxy.pluginManager.registerCommand(this, CommandManager("shout", "", "shout", "hh", "shoutall"))
    }


    override fun onDisable() {

    }
}