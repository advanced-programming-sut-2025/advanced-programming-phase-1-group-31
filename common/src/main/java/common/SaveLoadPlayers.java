package common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.*;
import java.util.ArrayList;

public class SaveLoadPlayers {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void savePlayers(ArrayList<UserInfo> players) {
        String sql = "INSERT OR REPLACE INTO users(username, password, nickname, lobby_json) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (UserInfo p : players) {
                    ps.setString(1, p.getUsername());
                    ps.setString(2, p.getPassword());
                    ps.setString(3, p.getNickName());

                    String lobbyJson = p.getLobby() == null ? null : gson.toJson(p.getLobby());
                    ps.setString(4, lobbyJson);

                    ps.addBatch();
                }
                ps.executeBatch();
            }
            conn.commit();
        } catch (SQLException ignored) {
        }
    }

    public static ArrayList<UserInfo> loadPlayers() {
        ArrayList<UserInfo> players = new ArrayList<>();
        String sql = "SELECT username, password, nickname, lobby_json FROM users";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                String lobbyJson = rs.getString("lobby_json");

                Lobby lobby = null;
                if (lobbyJson != null) {
                    try {
                        lobby = gson.fromJson(lobbyJson, Lobby.class);
                    } catch (Exception ignore) {
                    }
                }

                players.add(new UserInfo(username, password, nickname, lobby));
            }

        } catch (SQLException ignored) {
        }
        return players;
    }

}
