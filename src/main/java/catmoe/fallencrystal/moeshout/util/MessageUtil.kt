package catmoe.fallencrystal.moeshout.util

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.connection.ProxiedPlayer

object MessageUtil {
    fun sendChat(p: ProxiedPlayer, message: String) {
        p.sendMessage(ChatMessageType.CHAT, TextComponent(ca(message)))
    }

    fun ca(text: String): String { return ChatColor.translateAlternateColorCodes('&', text) }
}