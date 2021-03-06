/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.blocks.drain

import net.bdew.pressure.api.IPressureConnectableBlock
import net.bdew.pressure.blocks.{BaseIOBlock, BlockNotifyUpdates}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection

object BlockDrain extends BaseIOBlock("drain", classOf[TileDrain]) with BlockNotifyUpdates with IPressureConnectableBlock {
  override def canConnectTo(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection) =
    getFacing(world, x, y, z) == side.getOpposite
}
