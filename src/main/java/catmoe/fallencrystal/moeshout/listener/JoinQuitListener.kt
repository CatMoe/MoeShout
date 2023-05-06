package catmoe.fallencrystal.moeshout.listener

import catmoe.fallencrystal.moeshout.util.cache.DisplayCache
import catmoe.fallencrystal.moeshout.util.cache.ServerUUID
import net.md_5.bungee.api.event.PostLoginEvent
import net.md_5.bungee.api.event.ServerDisconnectEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler

class JoinQuitListener : Listener {
    @EventHandler
    fun writeName(event: ServerDisconnectEvent) {
        val uuid = event.player.uniqueId
        val cache = DisplayCache
        val prefix = cache.getPrefix(uuid)
        val suffix = cache.getSuffix(uuid)
        val name = event.player.name
        if (prefix != null && suffix != null) {
            DisplayCache.writeOfflineName(uuid, "$prefix$name$suffix")
        }
    }

    @EventHandler
    fun delName(event: PostLoginEvent) {
        DisplayCache.removeOfflineName(event.player.uniqueId)
        ServerUUID.invalidateServer(event.player)
    }

}