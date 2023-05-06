package catmoe.fallencrystal.moeshout.util.hook

import litebans.api.Database
import net.md_5.bungee.api.connection.ProxiedPlayer

class LiteBansMute() {


    @Suppress("DEPRECATION")
    @Synchronized
    fun isMuted(player: ProxiedPlayer): Boolean {
        return Database.get().isPlayerMuted(player.uniqueId, player.address.hostString)
    }

}