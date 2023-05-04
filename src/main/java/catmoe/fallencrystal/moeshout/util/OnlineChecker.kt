package catmoe.fallencrystal.moeshout.util

import net.md_5.bungee.api.config.ServerInfo
import java.net.InetSocketAddress
import java.net.Socket

object OnlineChecker {
    fun socketPing(server: ServerInfo): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress(server.address.address, server.address.port), 1000)
            socket.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}