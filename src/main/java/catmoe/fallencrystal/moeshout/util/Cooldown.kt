package catmoe.fallencrystal.moeshout.util

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.UUID
import java.util.concurrent.TimeUnit

object Cooldown {
    private val cooldownCache = Caffeine.newBuilder().build<UUID, Long>()

    val currentTime = System.currentTimeMillis()

    fun isCooldown(uuid: UUID): Boolean {
        val timestamp = cooldownCache.getIfPresent(uuid)
        return timestamp != null && timestamp > currentTime
    }

    fun getCooldown(uuid: UUID, timeUnit: TimeUnit): Long {
        val timestamp = cooldownCache.getIfPresent(uuid)
        if (timestamp == null || timestamp <= currentTime) { return 0}
        return timeUnit.convert(timestamp - currentTime, TimeUnit.MILLISECONDS)
    }

    fun setCooldown(uuid: UUID, duration: Long, timeUnit: TimeUnit) { cooldownCache.put(uuid, currentTime + timeUnit.toMillis(duration)) }

}