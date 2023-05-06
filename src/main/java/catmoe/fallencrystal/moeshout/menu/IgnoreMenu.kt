package catmoe.fallencrystal.moeshout.menu

import catmoe.fallencrystal.moeshout.util.cache.DisplayCache
import catmoe.fallencrystal.moeshout.util.cache.IgnorePlayers
import catmoe.fallencrystal.moeshout.util.menu.ForceFormatCode
import catmoe.fallencrystal.moeshout.util.menu.GUIBuilder
import catmoe.fallencrystal.moeshout.util.menu.ItemBuilder
import com.github.benmanes.caffeine.cache.Caffeine
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.connection.ProxiedPlayer

class IgnoreMenu(yourself: ProxiedPlayer) : GUIBuilder() {

    private val playersCache = Caffeine.newBuilder().build<Int, ProxiedPlayer>()

    private val ignoredCache = IgnorePlayers.getIgnorePlayers(yourself)
    private val invType = if (ignoredCache.size < 10) {
        InventoryType.GENERIC_9X2
    } else if (ignoredCache.size < 19) {
        InventoryType.GENERIC_9X3
    } else if (ignoredCache.size < 28) {
        InventoryType.GENERIC_9X4
    } else if (ignoredCache.size < 37) {
        InventoryType.GENERIC_9X5
    } else {
        InventoryType.GENERIC_9X6
    }

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
    }

    override fun define(p: ProxiedPlayer?) {
        type(invType)
    }

    private fun setPlayerSlot(slot: Int, p: ProxiedPlayer) {
        val itemName = DisplayCache.getDisplayName(p)
        setItem(slot, ItemBuilder(ItemType.PLAYER_HEAD).name(ForceFormatCode.replaceFormat(itemName))
            .lore(ForceFormatCode.replaceFormat("&e点击来解除屏蔽!"))
            .build())
        playersCache.put(slot, p)
    }

    override fun onClick(click: InventoryClick?) {
        val slot = click!!.slot()
        val cache = playersCache.getIfPresent(slot)
        updateItems()
        if (cache != null) { IgnorePlayers.removeIgnorePlayers(player!!, cache) }
    }

}