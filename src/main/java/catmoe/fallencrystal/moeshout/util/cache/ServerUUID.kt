package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*
import java.util.concurrent.TimeUnit

object ServerUUID {

    private const val expireTime = 30

    private val serverUUIDCache = Caffeine.newBuilder().expireAfterWrite(expireTime.toLong(), TimeUnit.SECONDS).build<ProxiedPlayer, Pair<UUID, ServerInfo>>()

    fun generate(player: ProxiedPlayer): UUID {
        val cache = serverUUIDCache
        if (cache.getIfPresent(player) != null) { cache.invalidate(player) }
        val uuid = UUID.randomUUID()
        val server = player.server.info
        cache.put(player, Pair(uuid, server))
        return uuid
    }

    fun getServer(player: ProxiedPlayer, uuid: UUID): ServerInfo? {
        val cache = serverUUIDCache.getIfPresent(player)
        if (!player.isConnected) return null
        return if (cache != null && cache.first == uuid)  cache.second else null
    }

    fun invalidateServer(player: ProxiedPlayer) { serverUUIDCache.invalidate(player) }

}