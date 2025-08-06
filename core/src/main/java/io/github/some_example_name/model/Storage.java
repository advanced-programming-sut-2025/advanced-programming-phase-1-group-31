//package io.github.some_example_name.model;
//
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class Storage {
//    private static final String FILE_PATH = "src/main/resources/players.json";
//    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//    public static void savePlayers(List<Player> players){
//        try(FileWriter writer = new FileWriter(FILE_PATH)){
//            gson.toJson(players, writer);
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList<Player> loadPlayers(){
//        try(FileReader reader = new FileReader(FILE_PATH)){
//            Player[] players = gson.fromJson(reader, Player[].class);
//            if(players == null){
//                return new ArrayList<>();
//            }
//            return new ArrayList<>(Arrays.asList(players));
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//        return new ArrayList<>();
//    }
//}
