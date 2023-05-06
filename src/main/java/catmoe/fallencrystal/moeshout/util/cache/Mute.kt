package catmoe.fallencrystal.moeshout.util.cache

import catmoe.fallencrystal.moeshout.util.hook.LiteBansMute
import com.github.benmanes.caffeine.cache.Caffeine
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer

object Mute {

    private val muted = mutableListOf<ProxiedPlayer>()

    private const val hookLiteBans = true

    /*
    Int 用于指定模式.
    0: (不适用于db) 玩家未被禁言
    1: 玩家被禁言 (内置系统)
    2: 玩家被禁言 (挂钩的插件 例如LiteBans)
     */
    private val db = Caffeine.newBuilder().build<ProxiedPlayer, Int>()

    fun setMute(player: ProxiedPlayer, mode: Int) {
        if (mode == 0 && muted.contains(player) && db.getIfPresent(player) != null) {
            muted.remove(player)
            db.invalidate(player)
            return
        }
        if (muted.contains(player) && db.getIfPresent(player) != null) throw IllegalStateException("player already muted!")
        muted.add(player)
        db.put(player, mode)
    }

    fun getMuteMode(player: ProxiedPlayer): Int {
        if (!muted.contains(player) && db.getIfPresent(player) == null) return 0
        getFromLiteBans(player)
        return db.getIfPresent(player)!!
    }

    fun isMuted(player: ProxiedPlayer): Boolean { getFromLiteBans(player); return muted.contains(player) }

    private fun getFromLiteBans(player: ProxiedPlayer) {
        if (!hookLiteBans) return
        ProxyServer.getInstance().scheduler.runAsync(SharedPlugin.getPlugin()!!) {
            if (LiteBansMute().isMuted(player)) {
                muted.add(player)
                db.put(player, 2)
            } else if (muted.contains(player) && getMuteMode(player) == 2) {
                muted.remove(player)
                db.invalidate(player)
            }
        }
    }

}