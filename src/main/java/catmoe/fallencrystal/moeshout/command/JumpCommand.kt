package catmoe.fallencrystal.moeshout.command

import catmoe.fallencrystal.moeshout.util.cache.ServerUUID
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class JumpCommand(private val sender: CommandSender, private val args: Array<out String>?) {

    val proxy = ProxyServer.getInstance()

    val target = proxy.getPlayer(args!![2])
    val uuid = args!![3]

    fun execute() {
        if (sender !is ProxiedPlayer) return
    }

    private fun serverNotFound() {
        TODO()
    }

    private fun getServer() {
        ServerUUID.getServer(target, uuid)
    }


}