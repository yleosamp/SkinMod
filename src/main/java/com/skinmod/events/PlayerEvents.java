package com.skinmod.events;

import com.skinmod.util.SkinUtil;
import com.skinmod.util.SkinStorage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;

public class PlayerEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) event.getPlayer();
            
            // Pequeno delay para garantir que o jogador carregou completamente
            try {
                Thread.sleep(500);
                String savedSkin = SkinStorage.getSavedSkin(player.getGameProfile().getName());
                
                if (savedSkin != null) {
                    SkinUtil.applySkin(player, savedSkin);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
