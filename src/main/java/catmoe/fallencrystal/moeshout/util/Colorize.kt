package catmoe.fallencrystal.moeshout.util

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.connection.ProxiedPlayer

object Colorize {
    fun add(text: String): String { return ChatColor.translateAlternateColorCodes('&', text) }

    fun addWithPermission(player: ProxiedPlayer, text: String): String {
        if (player.hasPermission("moeshout.color.all")) return add(text)
        val formatCode = mapOf(
            "0" to "black",
            "1" to "dark_blue",
            "2" to "dark_green",
            "3" to "dark_aqua",
            "4" to "dark_red",
            "5" to "dark_purple",
            "6" to "gold",
            "7" to "gray",
            "8" to "dark_gray",
            "9" to "blue",
            "a" to "green",
            "b" to "aqua",
            "c" to "red",
            "d" to "light_purple",
            "e" to "yellow",
            "f" to "white",
            "m" to "strikethrough",
            "n" to "underline",
            "l" to "bold",
            "k" to "magic",
            "o" to "italic",
            "r" to "reset"
        )
        var colorizedMessage = text
        for ((format, permission) in formatCode) {
            if (player.hasPermission("moeshout.color.$permission")) {
                colorizedMessage = colorizedMessage.replace("&$format", "§$format") }
        }
        return colorizedMessage
    }
}