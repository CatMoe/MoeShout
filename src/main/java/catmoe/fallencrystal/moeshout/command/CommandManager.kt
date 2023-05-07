package catmoe.fallencrystal.moeshout.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.TabExecutor

class CommandManager(name: String?, permission: String?, vararg aliases: String?) : Command(name, permission, *aliases), TabExecutor {

    val proxy = ProxyServer.getInstance()

    override fun execute(sender: CommandSender?, args: Array<out String>?) {
        if (args?.get(1).equals(ignoreCase = true, other = "ignore")) { val ignore = IgnoreCommand(sender!!, args); ignore.execute() }
        else if (args?.get(1).equals("JumpTo")) { val jump = JumpCommand(sender!!, args); jump.execute() }
        else if (args?.get(1).equals(ignoreCase = true, other = "mute")) { val mute = MuteCommand(sender!!, args); mute.execute() }
        else if (args?.get(1).equals("ViewProfile")) { val vp = ViewProfileCommand(sender!!, args); vp.execute() }
        else if (args?.get(1).equals(ignoreCase = true, other = "unmute")) { val unmute = UnMuteCommand(sender!!, args); unmute.execute() }
    }

    override fun onTabComplete(sender: CommandSender?, args: Array<out String>?): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    private fun MessageBuilder(startIndex: Int, args: Array<out String>?): String {
        val message = StringBuilder()
        for (arg in startIndex until args!!.size) { message.append(args[arg]).append(" ") }
        return message as String
    }
}