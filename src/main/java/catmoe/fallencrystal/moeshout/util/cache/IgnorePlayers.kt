package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.connection.ProxiedPlayer

object IgnorePlayers {
    private val ignoredCache = Caffeine.newBuilder().build<ProxiedPlayer, MutableList<ProxiedPlayer>>()

    fun addIgnorePlayers(player: ProxiedPlayer, target: ProxiedPlayer) {
        val listPlayers = ignoredCache.getIfPresent(player) ?: mutableListOf()
        listPlayers.add(target)
        ignoredCache.put(player, listPlayers)
    }

    fun removeIgnorePlayers(player: ProxiedPlayer, target: ProxiedPlayer) {
        val listPlayers = ignoredCache.getIfPresent(player) ?: return
        listPlayers.remove(target)
        if (listPlayers.isEmpty()) { ignoredCache.invalidate(player) } else { ignoredCache.put(player, listPlayers) }
    }

    fun removeAllIgnorePlayers(player: ProxiedPlayer) { ignoredCache.invalidate(player) }

    fun getIgnorePlayers(player: ProxiedPlayer): List<ProxiedPlayer> { return ignoredCache.getIfPresent(player) ?: emptyList() }

}