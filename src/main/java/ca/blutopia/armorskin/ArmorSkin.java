package ca.blutopia.armorskin;

import ca.blutopia.armorskin.config.ModConfig;
import com.mojang.blaze3d.platform.InputConstants.Type;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.glfw.GLFW;


public class ArmorSkin implements ClientModInitializer {

    public static ModConfig ConfigInstance;

    private KeyMapping keynmap;

    @Override
    public void onInitializeClient() {

      keynmap = new KeyMapping( "key.armorskin.settings", Type.KEYSYM, GLFW.GLFW_KEY_F7, new Category( ResourceLocation.tryParse("key.category.armorskin") ));
      AutoConfig.register( ModConfig.class, JanksonConfigSerializer::new);
      ConfigInstance = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
      ClientTickEvents.END_CLIENT_TICK.register( this::settingsMenuListener);
    }
    private void settingsMenuListener( Minecraft client) {
      while (keynmap.isDown()) {
        Screen settings = AutoConfig.getConfigScreen( ModConfig.class, null).get();
        client.setScreen(settings);
      }
    }
}
