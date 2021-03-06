/*
 * Copyright (c) bdew, 2013 - 2014
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.pressure.items.configurator

import net.bdew.lib.Client
import net.bdew.lib.gui._
import net.bdew.lib.gui.widgets.Widget
import net.bdew.pressure.network.NetworkHandler
import net.minecraftforge.fluids.FluidStack

import scala.collection.mutable

class WidgetFluidSelector(p: Point, conf: GuiConfigurator, n: Int) extends Widget {
  override val rect = new Rect(p, 16, 16)
  def getFluid = conf.displayMap.get(n) map conf.allFluids map (_._2)

  override def handleTooltip(p: Point, tip: mutable.MutableList[String]) = {
    tip ++= getFluid map { fluid =>
      fluid.getLocalizedName(new FluidStack(fluid, 1))
    }
  }

  override def draw(mouse: Point) {
    getFluid map { fluid =>
      parent.drawTexture(rect, Texture(Texture.BLOCKS, fluid.getIcon), Color.fromInt(fluid.getColor))
    }
  }

  override def mouseClicked(p: Point, button: Int) {
    getFluid map { fluid =>
      NetworkHandler.sendToServer(MsgSetFluidFilter(fluid.getName))
      Client.player.closeScreen()
    }
  }
}
