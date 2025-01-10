package com.skinmod;

import com.skinmod.commands.SkinCommand;
import com.skinmod.events.PlayerEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("skinmod")
public class SkinMod {
    public SkinMod() {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
    }

    @SubscribeEvent
    public void onServerStarting(RegisterCommandsEvent event) {
        SkinCommand.register(event.getDispatcher());
    }
}
