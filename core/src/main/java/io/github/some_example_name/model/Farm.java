package io.github.some_example_name.model;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import io.github.some_example_name.model.materials.Barn;
import io.github.some_example_name.model.materials.Coop;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private MapLayer objectLayer;
    private MapLayer blockLayer;
    private List<MapLayer> greenHouseLayer = new ArrayList<>();



    public Rectangle getObjectBounds(String objectName) {
        for (MapObject obj : objectLayer.getObjects()) {
            if (objectName.equalsIgnoreCase(obj.getName()) && obj instanceof RectangleMapObject) {
                return ((RectangleMapObject) obj).getRectangle();
            }
        }
        return null;
    }

    public List<MapObject> getObjectsByType(String type) {
        List<MapObject> result = new ArrayList<>();
        for (MapObject obj : objectLayer.getObjects()) {
            String objType = (String) obj.getProperties().get("type");
            if (type.equalsIgnoreCase(objType)) {
                result.add(obj);
            }
        }
        return result;
    }

    public MapObjects getAllObjects() {
        return objectLayer.getObjects();
    }

    public void setObjectLayer(MapLayer objectLayer) {
        this.objectLayer = objectLayer;
    }

    public void setBlockLayer(MapLayer blockLayer) {
        this.blockLayer = blockLayer;
    }

    public MapLayer getBlockLayer() {
        return blockLayer;
    }



    //delete
    private GreenHouse greenhouse;

    private ArrayList<Coop> coops = new ArrayList<>();
    private ArrayList<Barn> barns = new ArrayList<>();
    private Cottage cottage;
    private ArrayList<Quarry> quarryInFarm = new ArrayList<>();
    private ArrayList<Lake> lakeInFarm = new ArrayList<>();
    private Tile[][] mainMap ;
    private Rectangle rectangle ;



    public GreenHouse getGreenhouse() {
        return greenhouse;
    }

    public void setGreenhouse(GreenHouse greenhouse) {
        this.greenhouse = greenhouse;
    }

    public ArrayList<Coop> getCoops() {
        return coops;
    }

    public void setCoops(ArrayList<Coop> coops) {
        this.coops = coops;
    }

    public ArrayList<Barn> getBarns() {
        return barns;
    }

    public void setBarns(ArrayList<Barn> barns) {
        this.barns = barns;
    }

    public Cottage getCottage() {
        return cottage;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    public ArrayList<Quarry> getQuarryInFarm() {
        return quarryInFarm;
    }

    public void setQuarryInFarm(ArrayList<Quarry> quarryInFarm) {
        this.quarryInFarm = quarryInFarm;
    }

    public ArrayList<Lake> getLakeInFarm() {
        return lakeInFarm;
    }

    public void setLakeInFarm(ArrayList<Lake> lakeInFarm) {
        this.lakeInFarm = lakeInFarm;
    }

    public Tile[][] getMainMap() {
        return mainMap;
    }

    public void setMainMap(Tile[][] mainMap) {
        this.mainMap = mainMap;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public List<MapLayer> getGreenHouseLayer() {
        return greenHouseLayer;
    }

    public void setGreenHouseLayer(List<MapLayer> greenHouseLayer) {
        this.greenHouseLayer = greenHouseLayer;
    }
}

