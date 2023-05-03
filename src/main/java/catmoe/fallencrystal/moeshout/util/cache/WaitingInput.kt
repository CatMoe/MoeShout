package catmoe.fallencrystal.moeshout.util.cache

import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.connection.ProxiedPlayer

object WaitingInput {
    private val isWaitingCache = Caffeine.newBuilder().build<ProxiedPlayer, Int>()

    /*
    模式使用数字代替.
    0: 无
    1: 等待输入聊天
    2: 等待输入玩家
     */

    fun setIsWaiting(player: ProxiedPlayer, mode: Int) {
        val cache = isWaitingCache
        if (cache.getIfPresent(player) == null) { cache.put(player, mode) }
        else { if (cache.getIfPresent(player) == mode) return
            cache.invalidate(player)
            cache.put(player, mode)
        }
    }

    fun isWaitingMessage(player: ProxiedPlayer): Int { return isWaitingCache.getIfPresent(player) ?: 0 }

}