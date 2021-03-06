package net.blazenarchy.ream.player;

import com.google.gson.reflect.TypeToken;
import net.blazenarchy.ream.Ream;
import org.bukkit.OfflinePlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class PlayerDataUtilities {
    public static final String playerDataFolder = "plugins/Ream/player-data/";
    public static HashMap<UUID, PlayerData> playerData = new HashMap<>();

    public static PlayerData loadExistingPlayerData(UUID player) {
        PlayerData loadedData = playerData.getOrDefault(player, null);
        if (loadedData != null) {
            return loadedData;
        } else {
            return loadPlayerDataNoCreate(player);
        }
    }

    public static PlayerData loadPlayerData(OfflinePlayer player) {
        PlayerData loadedData = loadExistingPlayerData(player.getUniqueId());
        if (loadedData == null) {
            loadedData = new PlayerData(player).load();
        }
        loadedData.setUuidIfNull(player.getUniqueId());
        playerData.remove(player.getUniqueId());
        playerData.put(player.getUniqueId(), loadedData);
        return loadedData;
    }

    public static PlayerData loadPlayerDataNoCreate(UUID player) {
        File playerDataFile = new File(playerDataFolder + player.toString() + ".json");
        playerDataFile.getParentFile().mkdirs();
        try {
            return ((PlayerData) Ream.gson.fromJson(new FileReader(playerDataFile), new TypeToken<PlayerData>() {
            }.getType())).setUuidIfNull(player);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static HashSet<PlayerData> loadAllPlayerData() {
        HashSet<PlayerData> playerDataHashMap = new HashSet<>();
        File playerDataDir = new File(playerDataFolder);
        if (playerDataDir.isDirectory() && playerDataDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(playerDataDir.listFiles())) {
                try {
                    UUID playerID = UUID.fromString(file.getName().replace(".json", ""));
                    playerDataHashMap.add(loadExistingPlayerData(playerID));
                } catch (Exception ignored) {
                }
            }
        }
        return playerDataHashMap;
    }
}
