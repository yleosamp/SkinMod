package com.skinmod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.skinmod.util.SkinUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;

public class SkinCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skin")
            .then(Commands.argument("nickname", StringArgumentType.word())
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    String nickname = StringArgumentType.getString(context, "nickname");
                    
                    boolean success = SkinUtil.applySkin(player, nickname);
                    
                    if (success) {
                        context.getSource().sendSuccess(
                            new TextComponent("§aSkin aplicada com sucesso!"), false);
                    } else {
                        context.getSource().sendFailure(
                            new TextComponent("§cErro ao aplicar a skin."));
                    }
                    
                    return 1;
                })));
    }
}
