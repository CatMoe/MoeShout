package catmoe.fallencrystal.moeshout.menu

import catmoe.fallencrystal.moeshout.util.OnlineChecker
import catmoe.fallencrystal.moeshout.util.cache.DisplayCache
import catmoe.fallencrystal.moeshout.util.menu.ForceFormatCode
import catmoe.fallencrystal.moeshout.util.menu.GUIBuilder
import catmoe.fallencrystal.moeshout.util.menu.ItemBuilder
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.config.ServerInfo
import net.md_5.bungee.api.connection.ProxiedPlayer

class JumpMenu(private val yourself: ProxiedPlayer, targetPlayer: ProxiedPlayer, private val server: ServerInfo) : GUIBuilder() {

    val inventoryType = InventoryType.GENERIC_9X3

    val targetDisplayName = DisplayCache.getDisplayName(targetPlayer)

    private val denyItemName = ForceFormatCode.replaceFormat("&c忽略")
    private val denyItemSlot = 13
    private val denyItemStack = ItemType.REDSTONE_BLOCK

    private val acceptItemName = ForceFormatCode.replaceFormat("&a前往")
    private val acceptItemSlot = 18
    private val acceptItemStack = ItemType.EMERALD_BLOCK

    override fun open(player: ProxiedPlayer) {
        clear()
        define(player)
        super.open(player)
    }

    override fun define(p: ProxiedPlayer?) {
        super.define(p)
        type(inventoryType)
        setTitle(ForceFormatCode.replaceFormat("&b您已被&r$targetDisplayName&b邀请一个服务器!"))
        placeholderItem()
        setDenyItem()
        setAcceptItem()
    }

    private fun setInfoItem() {
        val slot = 15
        val itemType = ItemType.BEACON
        val serverIsOnline = if (OnlineChecker.socketPing(server)) "&a在线" else "&c离线"
        val serverName = server.name
        setItem(slot, ItemBuilder(itemType)
            .name(ForceFormatCode.replaceFormat("&b具体信息"))
            .lore(ca("&e邀请人: &r$targetDisplayName"))
            .lore("")
            .lore("&b服务器信息:")
            .lore(" &d服务器名称: &e$serverName")
            .lore(" &e状态: $serverIsOnline &7(${server.players.count()} 位玩家正在游玩)")
            .build()
        )
    }

    override fun onClick(click: InventoryClick?) {
        super.onClick(click)
        if (click!!.slot() == acceptItemSlot && click.clickedItem().itemType() == acceptItemStack) {
            if (yourself.server != server) { yourself.connect(server) }
        } else { updateItems() }
    }

    private fun setDenyItem() { setItem(denyItemSlot, ItemBuilder(denyItemStack).name(denyItemName).build()) }
    private fun setAcceptItem() { setItem(acceptItemSlot, ItemBuilder(acceptItemStack).name(acceptItemName).build()) }

    private fun placeholderItem() {
        val itemType = ItemType.GRAY_STAINED_GLASS_PANE
        val slots: List<Int> = mutableListOf(
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 18, 19, 20, 21, 22, 23,
            24, 25, 26
        )
        slots.forEach { setItem(it, ItemBuilder(itemType).name("").build()) }
    }

    private fun ca(message: String): String { return ForceFormatCode.replaceFormat(message) }

}