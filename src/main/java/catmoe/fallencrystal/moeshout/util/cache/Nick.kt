package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.connection.ProxiedPlayer

object Nick {
    // Nicked player list
    private val nickedList = mutableListOf<ProxiedPlayer>()

    // Name
    private val nickedName = Caffeine.newBuilder().build<ProxiedPlayer, String>()
    // Prefix & Suffix
    private val nickedPrefix = Caffeine.newBuilder().build<ProxiedPlayer, String>()
    private val nickedSuffix = Caffeine.newBuilder().build<ProxiedPlayer, String>()

    fun setPrefix(player: ProxiedPlayer, str: String) {
        if (!nickedList.contains(player)) throw IllegalStateException("Player ${player.uniqueId} is not nicked!")
        if (nickedPrefix.getIfPresent(player) != null) { nickedPrefix.invalidate(player) }
        nickedPrefix.put(player, str)
    }

    fun setSuffix(player: ProxiedPlayer, str: String) {
        if (!nickedList.contains(player)) throw IllegalStateException("Player ${player.uniqueId} is not nicked!")
        if (nickedSuffix.getIfPresent(player) != null) { nickedSuffix.invalidate(player) }
        nickedSuffix.put(player, str)
    }

    fun setNick(player: ProxiedPlayer, nickname: String, prefix: String, suffix: String) {
        if (nickedList.contains(player)) throw ConcurrentModificationException("Player ${player.uniqueId} is already nicked!")
        nickedName.put(player, nickname)
        nickedPrefix.put(player, prefix)
        nickedSuffix.put(player, suffix)
        nickedList.add(player)
    }

    fun setNick(player: ProxiedPlayer, nickname: String) {
        if (nickedList.contains(player)) throw ConcurrentModificationException("Player ${player.uniqueId} is already nicked!")
        nickedName.put(player, nickname)
        nickedPrefix.put(player, "")
        nickedSuffix.put(player, "")
        nickedList.add(player)
    }

    fun unNick(player: ProxiedPlayer) {
        nickedList.remove(player)
        nickedName.invalidate(player)
        nickedPrefix.invalidate(player)
        nickedSuffix.invalidate(player)
    }

    fun getNickDisplayName(player: ProxiedPlayer): String {
        if (!nickedList.contains(player)) throw NullPointerException("Player ${player.uniqueId} is not nicked!")
        val name = nickedName.getIfPresent(player)
        val prefix = nickedPrefix.getIfPresent(player)
        val suffix = nickedSuffix.getIfPresent(player)
        return "$prefix$name$suffix"
    }

    fun getNickName(player: ProxiedPlayer): String? {
        if (!nickedList.contains(player)) throw NullPointerException("Player ${player.uniqueId} is not nicked!")
        return nickedName.getIfPresent(player)
    }

    fun isNicked(player: ProxiedPlayer): Boolean { return nickedList.contains(player) }

    fun getDisplayNameWithoutNick(player: ProxiedPlayer): String {
        if (!nickedList.contains(player)) throw IllegalStateException("Player ${player.uniqueId} is not nicked! Please use DisplayCache.getDisplayName to get display name!")
        val uuid = player.uniqueId
        val realPrefix = DisplayCache.getPrefix(uuid)
        val realSuffix = DisplayCache.getSuffix(uuid)
        return "$realPrefix${player.name}$realSuffix"
    }

}