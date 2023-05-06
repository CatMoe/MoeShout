package catmoe.fallencrystal.moeshout.command

import catmoe.fallencrystal.moeshout.util.cache.Mute
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

class MuteCommand(private val sender: CommandSender, private val args: Array<out String>?) {
    fun execute() {
        if (!sender.hasPermission("moeshout.staff.mute")) { noPermission(); return }
        if (args!![2].isEmpty()) return
        val player = ProxyServer.getInstance().getPlayer(args[2])
        if (player == null) { playerNotFound(); return }
        if (Mute.isMuted(player)) { alreadyMuted(player); return }
        Mute.setMute(player, 1)
    }

    private fun alreadyMuted(player: ProxiedPlayer) {
        // 模式2 = 被其它插件禁言. 有关模式的更多信息 参阅 Mute.kt 中的 getMuteMode.
        if (Mute.getMuteMode(player) == 2) {
            TODO() // 当玩家是被LiteBans禁言的时候 什么? 你说为什么是TODO? 因为我懒(
        } else {
            Mute.setMute(player, 0) // 模式0 = 解除禁言
        }
    }

    private fun playerNotFound() {
        TODO()
    }

    private fun noPermission() {
        TODO()
    }

}