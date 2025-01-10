package com.skinmod.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.level.ServerPlayer;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Base64;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import java.util.Collections;

public class SkinUtil {
    public static boolean applySkin(ServerPlayer player, String nickname) {
        try {
            // URL da skin do MineSkin.eu
            String skinUrl = "https://mineskin.eu/skin/" + nickname;
            
            // Baixar a imagem da skin
            URL url = new URL(skinUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            if (conn.getResponseCode() != 200) {
                System.out.println("[SkinMod] Skin não encontrada para: " + nickname);
                return false;
            }

            // Ler a imagem
            BufferedImage skinImage = ImageIO.read(conn.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(skinImage, "png", baos);
            byte[] skinData = baos.toByteArray();
            
            // Converter para Base64
            String base64Skin = Base64.getEncoder().encodeToString(skinData);
            
            // Criar o JSON da textura
            String textureJson = "{\"textures\":{\"SKIN\":{\"url\":\"" + skinUrl + "\"}}}";
            String textureValue = Base64.getEncoder().encodeToString(textureJson.getBytes());

            // Aplicar a textura ao jogador
            GameProfile gameProfile = player.getGameProfile();
            gameProfile.getProperties().removeAll("textures");
            gameProfile.getProperties().put("textures", new Property("textures", textureValue, ""));

            // Atualizar visualmente para todos os jogadores
            ClientboundPlayerInfoPacket removePacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, player);
            ClientboundPlayerInfoPacket addPacket = new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, player);

            for (ServerPlayer p : player.getLevel().players()) {
                p.connection.send(removePacket);
                p.connection.send(addPacket);
            }

            System.out.println("[SkinMod] Skin aplicada com sucesso para: " + nickname);
            return true;

        } catch (Exception e) {
            System.out.println("[SkinMod] Erro ao aplicar skin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
