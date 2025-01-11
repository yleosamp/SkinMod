package com.skinmod.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkinStorage {
    private static final String STORAGE_FILE = "skin_data.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, String> playerSkins = new HashMap<>();

    public static void init() {
        load();
    }

    public static void saveSkin(String playerName, String skinNick) {
        playerSkins.put(playerName, skinNick);
        save();
    }

    public static String getSavedSkin(String playerName) {
        return playerSkins.get(playerName);
    }

    private static void save() {
        try (Writer writer = new FileWriter(STORAGE_FILE)) {
            GSON.toJson(playerSkins, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void load() {
        File file = new File(STORAGE_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                TypeToken<HashMap<String, String>> type = new TypeToken<>() {};
                playerSkins = GSON.fromJson(reader, type.getType());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
} 