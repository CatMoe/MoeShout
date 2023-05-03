package catmoe.fallencrystal.moeshout.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class CommandManager(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases), TabExecutor {

    val proxy = ProxyServer.getInstance()

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (args?.get(1).equals(ignoreCase = true, other = "ignore")) { val ignore = IgnoreCommand(sender!!, args); ignore.execute() }
        if (args?.get(1).equals("JumpTo")) { val jump = JumpCommand(sender!!, args); jump.execute() }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    private fun isPlayer(player: String): Boolean { return proxy.getPlayer(player) != null }
}