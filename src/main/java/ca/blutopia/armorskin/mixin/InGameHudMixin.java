package ca.blutopia.armorskin.mixin;

import ca.blutopia.armorskin.ArmorSkin;
import ca.blutopia.armorskin.DynamicArmorSkin;
import ca.blutopia.armorskin.config.ArmorType;
import ca.blutopia.armorskin.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

  @Unique
  private static final ResourceLocation ARMORSKIN_TEXTURE = new ResourceLocation("minecraft", "textures/gui/armorskin.png");
  @Unique
  private static final ModConfig ModConfig = ArmorSkin.ConfigInstance;
  @Unique
  private final DynamicArmorSkin dynamicArmorSkin = new DynamicArmorSkin( Minecraft.getInstance() );
  @Unique
  private int currentIconIndex = 0;

  @Redirect(method = "renderPlayerHealth",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 0))
  private void redirectArmorIcon(GuiGraphics graphics, ResourceLocation resource, int x, int y, int u, int v, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(ARMORSKIN_TEXTURE, x, y, armorType.u, armorType.v, width, height);
      currentIconIndex++;
    } else {
      graphics.blit(ARMORSKIN_TEXTURE, x, y, armorSkin.u, armorSkin.v, width, height);
    }
  }

  @Redirect(method = "renderPlayerHealth",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIIIII)V", ordinal = 1))
  private void redirectHalfArmorIcon(GuiGraphics graphics, ResourceLocation resource, int x, int y, int u, int v, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(ARMORSKIN_TEXTURE, x, y, armorType.u, armorType.v, width, height);
    } else {
      graphics.blit(ARMORSKIN_TEXTURE, x, y, armorSkin.u, armorSkin.v, width, height);
    }
  }

  @Inject(method = "renderPlayerHealth",
    at = @At(value = "HEAD"))
  private void resetIconIndex(GuiGraphics guiGraphics, CallbackInfo ci) {
    currentIconIndex = 0;
    dynamicArmorSkin.storeArmorValues();
  }
}
