package catmoe.fallencrystal.moeshout.command

import catmoe.fallencrystal.moeshout.util.cache.IgnorePlayers
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class IgnoreCommand(private val sender: CommandSender, private val args: Array<out String>?) {

    val proxy = ProxyServer.getInstance()
    fun execute() {
        if (sender !is ProxiedPlayer) return
        val target = proxy.getPlayer(args!![2])!!
        IgnorePlayers.addIgnorePlayers(sender, target)
    }

}