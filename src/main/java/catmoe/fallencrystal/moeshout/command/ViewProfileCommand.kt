package catmoe.fallencrystal.moeshout.command

import catmoe.fallencrystal.moeshout.menu.ViewProfileMenu
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class ViewProfileCommand(private val sender: CommandSender, private val args: Array<out String>?) {

    // 2 = UUID

    val proxy = ProxyServer.getInstance()

    fun execute() {
        if (args?.get(2).isNullOrEmpty() || !sender.hasPermission("moeshout.staff.view-profile")) return
        val target = proxy.getPlayer(args?.get(2))
        if (target != null && target.isConnected)  {
            val menu = ViewProfileMenu(target, sender as ProxiedPlayer)
            menu.open(sender)
        }
    }

    private fun targetNotOnline() {
        TODO()
    }
}