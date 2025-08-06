package io.github.some_example_name.model;

import com.badlogic.gdx.maps.tiled.TmxMapLoader; import com.badlogic.gdx.maps.tiled.TiledMap; import com.badlogic.gdx.maps.tiled.TmxMapLoader.Parameters;

import java.util.HashMap; import java.util.Map; import java.util.Objects;

public class MapInstanceManager { private final Map<Integer, Map<MapType, FarmMap>> playerMaps = new HashMap<>();

    /**
     * Gets or creates a FarmMap for the specified player and map type.
     *
     * @param playerId the ID of the player
     * @param type     the type of map to get or create
     * @return the FarmMap instance for the specified player and type
     * @throws NullPointerException if either playerId or type is null
     */
    public FarmMap getPlayerMap(Integer playerId, MapType type) {
        Objects.requireNonNull(playerId, "Player ID cannot be null");
        Objects.requireNonNull(type, "MapType cannot be null");

        return playerMaps
            .computeIfAbsent(playerId, k -> new HashMap<>())
            .computeIfAbsent(type, t -> {
                FarmMap newMap = createNewFarmMap(t);
                System.out.println("[MapInstanceManager] Created new map for player " + playerId + " type " + t + " → " + System.identityHashCode(newMap.getTmxMap()));
                return newMap;
            });
    }

    /**
     * Creates a new FarmMap instance with the TMX map loaded (always creates new instance).
     *
     * @param type the map type to create
     * @return a new FarmMap instance
     */
    private FarmMap createNewFarmMap(MapType type) {
        Parameters parameters = new Parameters();
        parameters.generateMipMaps = false;
        parameters.convertObjectToTileSpace = false;

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tiledMap = loader.load(type.getFilename(), parameters);

        FarmMap farmMap = new FarmMap();
        farmMap.setTmxMap(tiledMap);
        return farmMap;
    }

}
