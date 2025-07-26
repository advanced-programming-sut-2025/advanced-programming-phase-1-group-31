package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Type;

public class SaveLoadPlayers {
    private static final String filePath = "userinfo/players.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public static void savePlayers(ArrayList<Player> players) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(players, writer);
        } catch (IOException ignore) {}
    }

    public static ArrayList<Player> loadPlayers() {
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException ignore) {
            return new ArrayList<>();
        }
    }
}
