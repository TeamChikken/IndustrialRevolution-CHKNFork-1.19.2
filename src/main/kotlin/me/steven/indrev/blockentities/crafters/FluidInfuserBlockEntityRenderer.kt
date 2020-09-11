package me.steven.indrev.blockentities.crafters

import alexiil.mc.lib.attributes.fluid.render.FluidRenderFace
import alexiil.mc.lib.attributes.fluid.render.FluidVolumeRenderer
import me.steven.indrev.blocks.machine.HorizontalFacingMachineBlock
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.math.Direction

class FluidInfuserBlockEntityRenderer(dispatcher: BlockEntityRenderDispatcher) : BlockEntityRenderer<FluidInfuserBlockEntity>(dispatcher) {
    override fun render(
        entity: FluidInfuserBlockEntity?,
        tickDelta: Float,
        matrices: MatrixStack?,
        vertexConsumers: VertexConsumerProvider?,
        light: Int,
        overlay: Int
    ) {
        val fluidComponent = entity?.fluidComponent ?: return
        val inputFace = when (entity.cachedState[HorizontalFacingMachineBlock.HORIZONTAL_FACING]) {
            Direction.NORTH -> INPUT_NORTH_FACE
            Direction.SOUTH -> INPUT_SOUTH_FACE
            Direction.WEST -> INPUT_WEST_FACE
            Direction.EAST -> INPUT_EAST_FACE
            else -> return
        }
        val inputVolume = fluidComponent.tanks[0].volume
        if (!inputVolume.isEmpty) {
            inputVolume.render(inputFace, FluidVolumeRenderer.VCPS, matrices)
            FluidVolumeRenderer.VCPS.draw()
        }

        val outputFace = when (entity.cachedState[HorizontalFacingMachineBlock.HORIZONTAL_FACING]) {
            Direction.NORTH -> OUTPUT_NORTH_FACE
            Direction.SOUTH -> OUTPUT_SOUTH_FACE
            Direction.WEST -> OUTPUT_WEST_FACE
            Direction.EAST -> OUTPUT_EAST_FACE
            else -> return
        }
        val outputVolume = fluidComponent.tanks[1].volume
        if (!outputVolume.isEmpty) {
            outputVolume.render(outputFace, FluidVolumeRenderer.VCPS, matrices)
            FluidVolumeRenderer.VCPS.draw()
        }
    }

    companion object {
        private val INPUT_NORTH_FACE =
            listOf(FluidRenderFace.createFlatFaceZ(0.815, 0.690, -0.005, 0.19, 0.815, -0.005, 2.0, true, false))
        private val INPUT_SOUTH_FACE =
            listOf(FluidRenderFace.createFlatFaceZ(0.185, 0.690, 1.005, 0.81, 0.815, 1.005, 2.0, true, false))
        private val INPUT_WEST_FACE =
            listOf(FluidRenderFace.createFlatFaceX(-0.005, 0.690, 0.185, -0.005, 0.815, 0.81, 2.0, false, false))
        private val INPUT_EAST_FACE =
            listOf(FluidRenderFace.createFlatFaceX(1.005, 0.690, 0.815, 1.005, 0.815, 0.19, 2.0, false, false))

        private val OUTPUT_NORTH_FACE =
            listOf(FluidRenderFace.createFlatFaceZ(0.815, 0.190, -0.005, 0.19, 0.32, -0.005, 2.0, true, false))
        private val OUTPUT_SOUTH_FACE =
            listOf(FluidRenderFace.createFlatFaceZ(0.185, 0.190, 1.005, 0.81, 0.32, 1.005, 2.0, true, false))
        private val OUTPUT_WEST_FACE =
            listOf(FluidRenderFace.createFlatFaceX(-0.005, 0.190, 0.185, -0.005, 0.32, 0.81, 2.0, false, false))
        private val OUTPUT_EAST_FACE =
            listOf(FluidRenderFace.createFlatFaceX(1.005, 0.190, 0.815, 1.005, 0.32, 0.19, 2.0, false, false))
    }
}