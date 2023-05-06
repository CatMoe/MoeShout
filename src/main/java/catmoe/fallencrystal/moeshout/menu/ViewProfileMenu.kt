package catmoe.fallencrystal.moeshout.menu

import catmoe.fallencrystal.moeshout.util.cache.DisplayCache
import catmoe.fallencrystal.moeshout.util.cache.IgnorePlayers
import catmoe.fallencrystal.moeshout.util.cache.Mute
import catmoe.fallencrystal.moeshout.util.menu.ForceFormatCode
import catmoe.fallencrystal.moeshout.util.menu.GUIBuilder
import catmoe.fallencrystal.moeshout.util.menu.ItemBuilder
import dev.simplix.protocolize.api.Protocolize
import dev.simplix.protocolize.api.inventory.InventoryClick
import dev.simplix.protocolize.data.ItemType
import dev.simplix.protocolize.data.inventory.InventoryType
import net.md_5.bungee.api.connection.ProxiedPlayer

class ViewProfileMenu(private val target: ProxiedPlayer, private val yourself: ProxiedPlayer) : GUIBuilder() {

    val targetDisplayName = DisplayCache.getDisplayName(target)

    val muteSlot = 28
    val ignoreSlot = 30
    val serverSlot = 32
    val unNickSlot = 34

    override fun open(player: ProxiedPlayer) {
        clear()
        define(yourself)
        open(yourself)
    }

    override fun define(p: ProxiedPlayer?) {
        type(InventoryType.GENERIC_9X5)
        setTitle(ca("$targetDisplayName 的档案"))
        setInfoItem()
        setActionButton()
    }

    private fun setInfoItem() {
        val slot = 13
        val item = ItemType.PLAYER_HEAD
        // 当玩家在匿名状态下 会显示是并且加上原来的名字 如果玩家未匿名使用getDisplayNameWithoutNick 则会抛出NPE(NullPointerException)
        val nick = if (DisplayCache.isNicked(target)) "&a是  &7[ ${DisplayCache.getDisplayNameWithoutNick(target)} &7]" else "&c否"
        val server = target.server.info.name
        val protocolPlayer = Protocolize.playerProvider().player(target.uniqueId)
        val location = protocolPlayer.location()
        val muteMode = Mute.getMuteMode(target)
        val isMuted = if (muteMode == 0) "&c否" else if (muteMode == 1) "&a是" else if (muteMode == 2) "&a是 &7(由Litebans禁言)" else "&c未知"
        val isIgnored = if (IgnorePlayers.getIgnorePlayers(yourself).contains(target)) "&a是" else "&c否"
        setItem(slot, ItemBuilder(item)
            .name(ca("$targetDisplayName 的档案"))
            .lore(ca("&b是否匿名?  $nick"))
            .lore(ca("&e位置: 在 $server 服务器的 $location 位置"))
            .lore(ca("&a已被禁言?: $isMuted"))
            .lore(ca("&c已被您屏蔽?: $isIgnored"))
            .skullOwner(target.name)
            .build()
        )
    }

    private fun setActionButton() {

        // Mute
        if (Mute.isMuted(target)) {
            setItem(muteSlot, ItemBuilder(ItemType.EMERALD_ORE).name(ca("&a解除禁言")).build())
        } else {
            setItem(muteSlot, ItemBuilder(ItemType.REDSTONE_ORE).name(ca("&c禁言")).build())
        }

        // Ignore
        if (IgnorePlayers.getIgnorePlayers(yourself).contains(target)) {
            setItem(ignoreSlot, ItemBuilder(ItemType.EMERALD).name(ca("&a解除屏蔽")).build())
        } else {
            setItem(ignoreSlot, ItemBuilder(ItemType.REDSTONE).name(ca("&c屏蔽")).build())
        }

        // toServer
        if (yourself.server.info == target.server.info) {
            setItem(serverSlot, ItemBuilder(ItemType.BARRIER).name(ca("&e传送到目标服务器")).lore(ca("&c您已经跟此玩家在一个服务器内了!")).build())
        } else {
            setItem(serverSlot, ItemBuilder(ItemType.ENDER_EYE).name(ca("&e传送到目标服务器")).build())
        }

        // unNick
        if (DisplayCache.isNicked(target)) {
            setItem(unNickSlot, ItemBuilder(ItemType.NAME_TAG).name("&b取消匿名").build())
        } else {
            setItem(unNickSlot, ItemBuilder(ItemType.BARRIER).name("&b取消匿名").lore(ca("&c此玩家没有匿名!")).build())
        }
    }

    override fun onClick(click: InventoryClick?) {
        val slot = click!!.slot()
        val item = click.clickedItem().itemType()
        // unNick
        if (slot == unNickSlot  && item == ItemType.NAME_TAG) {
            if (DisplayCache.isNicked(target)) { DisplayCache.unNickname(target); open(yourself) } else { playerIsNotNicked(); open(yourself) }
        }
        // toServer
        else if (slot == serverSlot && item == ItemType.ENDER_EYE) { yourself.connect(target.server.info); open(yourself) }
        // Ignore
        else if (click.slot() == ignoreSlot) {
            if (item == ItemType.REDSTONE) {
                if (target.hasPermission("moeshout.chat.bypass.ignore")) { targetHasBypassIgnorePermission(); open(yourself); return }
                IgnorePlayers.addIgnorePlayers(yourself, target); open(yourself)
            } else if (item == ItemType.EMERALD) {
                IgnorePlayers.removeIgnorePlayers(yourself, target); open(yourself)
            }
        } else if (slot == muteSlot) {
            if (item == ItemType.REDSTONE_ORE) {
                if (target.hasPermission("moeshout.chat.bypass.mute")) { targetHasBypassMutePermission(); open(yourself); return }
                Mute.setMute(target, 1)
            } else if (item == ItemType.EMERALD_ORE) { Mute.setMute(target, 0) }
        } else { updateItems() }
    }

    fun ca(text: String): String { return ForceFormatCode.replaceFormat(text) }

    private fun playerIsNotNicked() {
        TODO()
    }

    private fun targetHasBypassIgnorePermission() {
        TODO()
    }

    private fun targetHasBypassMutePermission() {
        TODO()
    }

}