package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.*

object OfflineDisplayCache {
    private val offlineDisplayName = Caffeine.newBuilder().build<UUID, String>()


    fun writeOfflineName(uuid: UUID, name: String) {
        val cache = offlineDisplayName
        if (cache.getIfPresent(uuid) != null) { cache.invalidate(uuid) }
        cache.put(uuid, name)
    }

    fun getOfflineName(uuid: UUID): String { return offlineDisplayName.getIfPresent(uuid) ?: ""}

    fun removeOfflineName(uuid: UUID) {
        val cache = offlineDisplayName
        if (cache.getIfPresent(uuid) != null) return
        cache.invalidate(uuid)
    }

}