package ca.blutopia.armorskin.mixin;

import ca.blutopia.armorskin.ArmorSkin;
import ca.blutopia.armorskin.DynamicArmorSkin;
import ca.blutopia.armorskin.config.ArmorType;
import ca.blutopia.armorskin.config.ModConfig;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public abstract class InGameHudMixin {

  @Unique
  private static final Identifier ARMORSKIN_TEXTURE = Identifier.withDefaultNamespace("textures/gui/armorskin.png");
  @Unique
  private static final Identifier GLINT_TEXTURE = Identifier.withDefaultNamespace("textures/misc/enchanted_glint_item.png");
  @Unique
  private static final ModConfig ModConfig = ArmorSkin.ConfigInstance;
  @Unique
  private static final DynamicArmorSkin dynamicArmorSkin = new DynamicArmorSkin(Minecraft.getInstance());
  @Unique
  private static int currentIconIndex = 0;
  @Unique
  private static boolean hasEnchantedArmor = false;

  @Inject(method = "extractArmor", at = @At("HEAD"))
  private static void capturePlayerForGlint(GuiGraphicsExtractor graphics, Player player, int y, int lines, int emptySlots, int x, CallbackInfo ci) {
      hasEnchantedArmor = false;
      for (net.minecraft.world.entity.EquipmentSlot slot : net.minecraft.world.entity.EquipmentSlot.values()) {
          if (slot.isArmor() && player.getItemBySlot(slot).hasFoil()) {
              hasEnchantedArmor = true;
              break;
          }
      }
  }

  @Unique
  private static void drawGlint(GuiGraphicsExtractor graphics, int x, int y, int width, int height) {
      if (hasEnchantedArmor) {
          graphics.blit(RenderPipelines.GLINT, GLINT_TEXTURE, x, y, 0, 0, width, height, width, height);
      }
  }

  @Redirect(method = "extractArmor",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 0))
  private static void redirectArmorIcon( GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier resource, int x, int y, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorType.u, armorType.v, width, height, 256, 256);
      drawGlint(graphics, x, y, width, height);
      currentIconIndex++;
    } else {
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorSkin.u, armorSkin.v, width, height, 256, 256);
      drawGlint(graphics, x, y, width, height);
    }
  }

  @Redirect(method = "extractArmor",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 1))
  private static void redirectHalfArmorIcon(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline , Identifier resource, int x, int y, int width, int height) {
    ArmorType armorSkin = ModConfig.armorSkin;

    if (armorSkin == ArmorType.DYNAMIC) {
      ArmorType armorType = dynamicArmorSkin.getArmorTypeForIcon(currentIconIndex);
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorType.u + 9, armorType.v, width, height, 256, 256);
      drawGlint(graphics, x, y, width, height);
      currentIconIndex++;
    } else {
      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, armorSkin.u + 9, armorSkin.v, width, height, 256, 256);
      drawGlint(graphics, x, y, width, height);
    }
  }

  @Redirect(method = "extractArmor",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V", ordinal = 2))
  private static void redirectEmptyArmorIcon(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline , Identifier resource, int x, int y, int width, int height) {

    if (ModConfig.showElytra && dynamicArmorSkin.isElytraEquipped()) {

      if (currentIconIndex != ModConfig.elytraIconPosition) {
        graphics.blitSprite( renderPipeline, resource, x, y, width, height );
        currentIconIndex++;
        return;
      }

      graphics.blit(renderPipeline, ARMORSKIN_TEXTURE, x, y, ArmorType.ELYTRA.u, ArmorType.ELYTRA.v, width, height, 256, 256);
      currentIconIndex++;
      return;
    }

    graphics.blitSprite( renderPipeline, resource, x, y, width, height );

  }

  @Inject(method = "extractPlayerHealth",
    at = @At(value = "HEAD"))
  private static void resetIconIndex( GuiGraphicsExtractor guiGraphics, CallbackInfo ci ) {
    currentIconIndex = 0;
    dynamicArmorSkin.storeArmorValues();
  }
}
