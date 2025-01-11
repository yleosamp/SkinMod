package com.skinmod.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.level.ServerPlayer;
import java.io.InputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Base64;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoPacket;
import java.util.Collections;

public class SkinUtil {
    public static boolean applySkin(ServerPlayer player, String nickname) {
        try {
            System.out.println("[SkinMod] Iniciando processo para: " + nickname);
            
            // Primeiro, pegar UUID do jogador
            String mojangApi = "https://api.mojang.com/users/profiles/minecraft/" + nickname;
            URL mojangUrl = new URL(mojangApi);
            HttpURLConnection mojangConn = (HttpURLConnection) mojangUrl.openConnection();
            mojangConn.setRequestMethod("GET");
            mojangConn.setRequestProperty("User-Agent", "Mozilla/5.0");
            
            if (mojangConn.getResponseCode() != 200) {
                System.out.println("[SkinMod] Jogador não encontrado: " + nickname);
                return false;
            }

            // Ler UUID da resposta
            InputStream is = mojangConn.getInputStream();
            StringBuilder response = new StringBuilder();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                response.append(new String(buffer, 0, bytesRead));
            }
            
            String responseStr = response.toString().trim();
            System.out.println("[SkinMod] Resposta da Mojang: " + responseStr);

            // Extrair UUID usando o formato correto da resposta
            String uuid = responseStr.split("\"id\" : \"")[1].split("\"")[0];
            System.out.println("[SkinMod] UUID encontrado: " + uuid);

            // Agora vamos pegar os dados da skin do SessionServer
            String sessionApi = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false";
            URL sessionUrl = new URL(sessionApi);
            HttpURLConnection sessionConn = (HttpURLConnection) sessionUrl.openConnection();
            sessionConn.setRequestMethod("GET");
            sessionConn.setRequestProperty("User-Agent", "Mozilla/5.0");

            is = sessionConn.getInputStream();
            response = new StringBuilder();
            while ((bytesRead = is.read(buffer)) != -1) {
                response.append(new String(buffer, 0, bytesRead));
            }

            String sessionResponse = response.toString();
            String value = sessionResponse.split("\"value\" : \"")[1].split("\"")[0];
            String signature = sessionResponse.split("\"signature\" : \"")[1].split("\"")[0];

            // Aplicar textura com signature
            GameProfile profile = player.getGameProfile();
            profile.getProperties().removeAll("textures");
            profile.getProperties().put("textures", new Property("textures", value, signature));

            // Atualizar visualmente
            for (ServerPlayer p : player.getLevel().players()) {
                p.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, Collections.singletonList(player)));
                p.connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, Collections.singletonList(player)));
            }

            // Fazer o pulo
            player.teleportTo(player.getX(), player.getY() + 0.1, player.getZ());

            System.out.println("[SkinMod] Skin aplicada com sucesso para: " + nickname);
            return true;

        } catch (Exception e) {
            System.out.println("[SkinMod] Erro ao aplicar skin: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
