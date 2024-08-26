package gg.aquatic.aquaticseries.lib.interactable2.impl.meg

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import gg.aquatic.aquaticseries.lib.block.impl.VanillaBlock
import gg.aquatic.aquaticseries.lib.fake.FakeObjectHandler
import gg.aquatic.aquaticseries.lib.fake.PacketBlock
import gg.aquatic.aquaticseries.lib.interactable2.AbstractSpawnedInteractable
import gg.aquatic.aquaticseries.lib.interactable2.AudienceList
import gg.aquatic.aquaticseries.lib.interactable2.InteractableInteractEvent
import gg.aquatic.aquaticseries.lib.interactable2.base.SpawnedInteractableBase
import org.bukkit.Location
import org.bukkit.Material
import kotlin.jvm.optionals.getOrNull

class SpawnedMegInteractable(
    override val location: Location,
    override val base: MegInteractable<*>,
    override val spawnedInteractableBase: SpawnedInteractableBase<*>
) : AbstractSpawnedInteractable<MegInteractable<*>>(), ISpawnedMegInteractable {

    val blocks = HashMap<Location, PacketBlock>()
    override val associatedLocations: Collection<Location>
        get() {
            return blocks.keys
        }

    override val dummy = MegInteractableDummy(this)

    init {
        spawnBlocks()
        spawnModel()
    }


    private fun spawnBlocks() {
        base.multiBlock.processLayerCells(location) { char, loc ->
            if (char != ' ') {
                val block = VanillaBlock(Material.AIR.createBlockData())
                val blockData = block.blockData
                val packetBlock = PacketBlock(loc, blockData, AudienceList(mutableListOf(), AudienceList.Mode.BLACKLIST)) {}
                packetBlock.spawn()
                blocks += loc to packetBlock
            }
        }
    }

    private fun spawnModel() {
        dummy.location = this.location
        dummy.bodyRotationController.yBodyRot = location.yaw
        dummy.bodyRotationController.xHeadRot = location.pitch
        dummy.bodyRotationController.yHeadRot = location.yaw
        dummy.yHeadRot = location.yaw
        dummy.yBodyRot = location.yaw
        val me = ModelEngineAPI.createModeledEntity(dummy)
        val am = ModelEngineAPI.createActiveModel(base.modelId)
        me.addModel(am,true)
    }

    override val modeledEntity: ModeledEntity?
        get() {
            return ModelEngineAPI.getModeledEntity(dummy.entityId)
        }

    override val activeModel: ActiveModel?
        get() {
            return modeledEntity?.getModel(base.modelId)?.getOrNull()
        }

    override fun despawn() {
        blocks.forEach {
            it.value.despawn()
            FakeObjectHandler.unregisterBlock(it.value.location)
        }
        blocks.clear()

        dummy.isRemoved = true
        modeledEntity?.destroy()
    }

    override fun onInteract(event: InteractableInteractEvent) {
        base.onInteract.accept(this, event)
    }
}