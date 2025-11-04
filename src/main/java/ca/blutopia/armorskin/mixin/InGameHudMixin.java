package ca.blutopia.armorskin.mixin;

import ca.blutopia.armorskin.ArmorSkin;
import ca.blutopia.armorskin.DynamicArmorSkin;
import ca.blutopia.armorskin.config.ArmorType;
import ca.blutopia.armorskin.config.ModConfig;
import com.mojang.blaze3d.pipeline.RenderPipeline;
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
  private static final ResourceLocation ARMORSKIN_TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/armorskin.png");
  @Unique
  private static final ModConfig ModConfig = ArmorSkin.ConfigInstance;
  @Unique
  private static final DynamicArmorSkin dynamicArmorSkin = new DynamicArmorSkin(Minecraft.getInstance());
  @Unique
  private static int currentIconIndex = 0;

  @Redirect(method = "renderArmor",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0))
  private static void redirectArmorIcon( GuiGraphics graphics, RenderPipeline renderPipeline, ResourceLocation resource, int x, int y, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorType.u, armorType.v, width, height, 256, 256);
      currentIconIndex++;
    } else {
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorSkin.u, armorSkin.v, width, height, 256, 256);
    }
  }

  @Redirect(method = "renderArmor",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1))
  private static void redirectHalfArmorIcon(GuiGraphics graphics, RenderPipeline renderPipeline , ResourceLocation resource, int x, int y, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorType.u, armorType.v, width, height, 256, 256);
      currentIconIndex++;
    } else {
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorSkin.u, armorSkin.v, width, height, 256, 256);
    }
  }

  @Inject(method = "renderPlayerHealth",
    at = @At(value = "HEAD"))
  private static void resetIconIndex( GuiGraphics guiGraphics, CallbackInfo ci ) {
    currentIconIndex = 0;
    dynamicArmorSkin.storeArmorValues();
  }
}
