/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * https://raw.github.com/bdew/pressure/master/MMPL-1.0.txt
 */

package net.bdew.pressure.render

import net.bdew.lib.render.{BaseBlockRenderHandler, RenderUtils}
import net.bdew.pressure.misc.Helper
import net.minecraft.block.Block
import net.minecraft.client.renderer.{RenderBlocks, Tessellator}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection
import org.lwjgl.opengl.GL11

import scala.collection.Set

object PipeRenderer extends BaseBlockRenderHandler {
  override def renderInventoryBlock(block: Block, metadata: Int, modelID: Int, renderer: RenderBlocks) {
    val tessellator = Tessellator.instance
    GL11.glPushMatrix()
    GL11.glScalef(0.8F, 0.8F, 0.8F)
    GL11.glTranslatef(-0.5F, -0.5F, -0.5F)

    block.setBlockBoundsForItemRender()
    renderer.setRenderBoundsFromBlock(block)

    for (side <- ForgeDirection.VALID_DIRECTIONS) {
      tessellator.startDrawingQuads()
      RenderUtils.doRenderItemSide(side, renderer, block, metadata)
      tessellator.draw()
    }

    GL11.glPopMatrix()
  }

  override def renderWorldBlock(world: IBlockAccess, x: Int, y: Int, z: Int, block: Block, modelId: Int, renderer: RenderBlocks): Boolean = {
    val sides = Helper.getPipeConnections(world, x, y, z).toSet
    val icon = if (renderer.hasOverrideBlockTexture)
      renderer.overrideBlockTexture
    else
      block.getIcon(0, 0)

    Tessellator.instance.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z))

    import net.bdew.pressure.render.RenderHelper._
    val offs = P3d(x, y, z)

    if (sides == Set(ForgeDirection.UP, ForgeDirection.DOWN)) {
      draw(ZNeg(P2d(0.75F, 1), P2d(0.25F, 0F), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      draw(ZPos(P2d(0.75F, 1), P2d(0.25F, 0F), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      draw(XNeg(P2d(0.75F, 1), P2d(0.25F, 0F), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      draw(XPos(P2d(0.75F, 1), P2d(0.25F, 0F), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
    } else if (sides == Set(ForgeDirection.EAST, ForgeDirection.WEST)) {
      draw(YNeg(P2d(1, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      draw(YPos(P2d(1, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      draw(ZNeg(P2d(1, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      draw(ZPos(P2d(1, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
    } else if (sides == Set(ForgeDirection.NORTH, ForgeDirection.SOUTH)) {
      draw(YNeg(P2d(0.75F, 1), P2d(0.25F, 0), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      draw(YPos(P2d(0.75F, 1), P2d(0.25F, 0), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      draw(XNeg(P2d(1, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      draw(XPos(P2d(1, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
    } else {

      if (sides.contains(ForgeDirection.UP)) {
        draw(ZNeg(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(ZPos(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XNeg(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XPos(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      }

      if (sides.contains(ForgeDirection.DOWN)) {
        draw(ZNeg(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(ZPos(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XNeg(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XPos(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
      }

      if (sides.contains(ForgeDirection.EAST)) {
        draw(YNeg(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(YPos(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(ZNeg(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(ZPos(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      }

      if (sides.contains(ForgeDirection.WEST)) {
        draw(YNeg(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(YPos(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(ZNeg(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(ZPos(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      }

      if (sides.contains(ForgeDirection.SOUTH)) {
        draw(YNeg(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(YPos(P2d(0.75F, 1), P2d(0.25F, 0.5F), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XNeg(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(XPos(P2d(1, 0.75F), P2d(0.5F, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      }

      if (sides.contains(ForgeDirection.NORTH)) {
        draw(YNeg(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.25F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(YPos(P2d(0.75F, 0.5F), P2d(0.25F, 0), 0.75F, PIcon(16, 14), PIcon(0, 2)), offs, icon)
        draw(XNeg(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.25F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
        draw(XPos(P2d(0.5F, 0.75F), P2d(0, 0.25F), 0.75F, PIcon(14, 16), PIcon(2, 0)), offs, icon)
      }

      draw(ZNeg(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.20F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
      draw(ZPos(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.80F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
      draw(XNeg(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.20F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
      draw(XPos(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.80F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
      draw(YPos(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.80F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
      draw(YNeg(P2d(0.80F, 0.80F), P2d(0.20F, 0.20F), 0.20F, PIcon(16, 16), PIcon(0, 0)), offs, icon)
    }
    true
  }
}
