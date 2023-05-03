package catmoe.fallencrystal.moeshout.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class JumpCommand(private val sender: CommandSender, private val args: Array<out String>?) {

    val proxy = ProxyServer.getInstance()

    fun execute() {
        val player = proxy.getPlayer(args!![2])
        val uuid = args[3]
    }

    private fun serverNotFound(player: ProxiedPlayer) {
        TODO()
    }

}