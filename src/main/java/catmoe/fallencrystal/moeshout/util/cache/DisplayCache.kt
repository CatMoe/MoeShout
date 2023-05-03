package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import net.luckperms.api.LuckPermsProvider
import net.md_5.bungee.api.connection.ProxiedPlayer
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

object DisplayCache {
    private const val prefixCacheExpire = 30
    private const val suffixCacheExpire = 30

    private val prefixCache = Caffeine.newBuilder().expireAfterWrite(prefixCacheExpire.toLong(), TimeUnit.SECONDS).build<UUID, String>()
    private val suffixCache = Caffeine.newBuilder().expireAfterWrite(suffixCacheExpire.toLong(), TimeUnit.SECONDS).build<UUID, String>()

    @Synchronized
    fun getPrefix(uuid: UUID): String? {
        val prefix = prefixCache.getIfPresent(uuid)
        return if (prefix == null) { val map = writeCache(uuid); map[1] } else { prefix }
    }

    @Synchronized
    fun getSuffix(uuid: UUID): String? {
        val suffix = suffixCache.getIfPresent(uuid)
        return if (suffix == null) { val map = writeCache(uuid); map[2] } else { suffix }
    }

    @Synchronized
    fun writeCache(uuid: UUID): Map<Int, String> {
        val user = LuckPermsProvider.get().userManager.getUser(uuid)
        val metaData = user?.cachedData?.metaData
        val prefix = metaData?.prefix
        val suffix = metaData?.suffix
        var prefixString = ""
        var suffixString = ""
        if (!prefix.isNullOrEmpty()) { prefixString = prefix }
        if (!suffix.isNullOrEmpty()) { suffixString = suffix }
        prefixCache.put(uuid, prefixString)
        suffixCache.put(uuid, suffixString)
        // 1 = prefix  2 = suffix
        val map: MutableMap<Int, String> = HashMap()
        map[1] = prefixString
        map[2] = suffixString
        return map
    }

    @Synchronized
    fun getDisplayName(p: ProxiedPlayer): String {
        val uuid = p.uniqueId
        val prefix = getPrefix(uuid)
        val suffix = getSuffix(uuid)
        val name = p.name
        return prefix + name + suffix
    }
}