package catmoe.fallencrystal.moeshout.command

import catmoe.fallencrystal.moeshout.util.cache.Mute
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class UnMuteCommand(private val sender: CommandSender, private val args: Array<out String>?) {

    val yourself = sender as ProxiedPlayer
    fun execute() {
        if (args?.get(2).isNullOrEmpty()) {
            if (Mute.isMuted(yourself)) { Mute.setMute(yourself, 0); isUnMuted(yourself) }
            else {playerIsNotMuted()}
        } else {
            val target = ProxyServer.getInstance().getPlayer(args?.get(2))
            if (target == null) { targetIsInvalid(); return }
            if (Mute.isMuted(target)) { Mute.setMute(target, 0); isUnMuted(target) }
            else { targetIsNotMuted() }
        }
    }

    private fun targetIsNotMuted() {
        TODO()
    }

    private fun playerIsNotMuted() {
        TODO()
    }

    private fun targetIsInvalid() {
        TODO()
    }

    private fun isUnMuted(player: ProxiedPlayer) {
        TODO()
    }
}