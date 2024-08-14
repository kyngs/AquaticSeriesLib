package gg.aquatic.aquaticseries.lib.inventory.lib.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import org.bukkit.event.inventory.InventoryClickEvent
import gg.aquatic.aquaticseries.lib.inventory.lib.component.MenuComponent
import gg.aquatic.aquaticseries.lib.inventory.lib.inventory.CustomInventory

class ComponentClickEvent(
    val inventory: CustomInventory,
    val component: MenuComponent,
    val originalEvent: InventoryClickEvent
): Event() {

    companion object {
        private val HANDLERS = HandlerList()

        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }

}