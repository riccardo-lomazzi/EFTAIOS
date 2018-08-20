package eftaios.model.board;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameBoard implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4506862421592792368L;
    private Map<String, Sector> board;
    private String mapName = "NOT_LOADED";
    private Sector humanStartingSector;
    private Sector alienStartingSector;
    private boolean mapLoaded;


    /**
     * Create a new game board based on the name of the map it will search the
     * file in the root directory of this project
     * 
     * @param mapName
     *            the name of the map to be loaded
     * @throws FileNotFoundException
     *             if the file is not found
     */
    public GameBoard(String mapName) throws FileNotFoundException {
        board = new HashMap<String, Sector>();
        mapLoaded=loadMap(mapName);
    }

    /**
     * check if a sector can move to a destination
     * 
     * @param from
     *            sector from which i'm moving
     * @param destination
     *            where i want to to be moved
     * @return the destination if the move was successful the from sector if not
     */
    public Sector moveTo(Sector from, Sector destination) {
        if (Sector.areNear(from, destination) && isDestinationWalkable(destination))
            return destination;
        else
            return from;
    }

    private boolean isDestinationWalkable(Sector to) {
        return board.get(to.getCompleteId()).isWalkable();
    }

    public Sector getHumanStartingSector() {
        return humanStartingSector;
    }

    public Sector getAlienStartingSector() {
        return alienStartingSector;
    }


    private boolean loadMap(String mapName) throws FileNotFoundException {
        Sector temp;
        /*
         * decoding the file to a string array
         */
        List<String> sectorsStoredAsStrings = generateArrayList(mapName);
        /*
         * Map files wrote in our format must have the map's name as the first
         * line generateArrayList(path) return the file as a list of string
         */
        this.mapName = sectorsStoredAsStrings.remove(0);
        for (String sectorAsString : sectorsStoredAsStrings) {
            temp = generateSector(sectorAsString);
            /*
             * The sector factory generates the sectors The two starting sectors
             * are saved locally for an easier access
             */
            if (compareIstanceOf(temp, new HumanStartingSector(null, null)))
                humanStartingSector = temp;
            if (compareIstanceOf(temp, new AlienStartingSector(null, null)))
                alienStartingSector = temp;
            board.put(temp.getCompleteId(), temp);
        }
        return true;
    }

    private boolean compareIstanceOf(Sector a, Sector b) {
        return a.getClass().isInstance(b);
    }

    private Sector generateSector(String sectorAsString) {
        return SectorFactory.createSector(sectorAsString);
    }

    private List<String> generateArrayList(String mapName) throws FileNotFoundException {
        List<String> temp = new ArrayList<String>();
        Scanner reader = null;
        reader = new Scanner(new File("./hexmaps/" + mapName + ".hexmap"));
        while (reader.hasNext()) {
            temp.add(reader.next());
        }
        reader.close();
        return temp;
    }

    public String getMapName() {
        return mapName;
    }

    /**
     * Get the sector in the map with the asked identifier
     * 
     * @param sectID
     *            the asked sector identifier
     * @return the asked sector if the identifier exist inside the board ,null
     *         if the sector doesn't exist or the identifier is invalid
     */
    public Sector getSector(String sectID) {
        return board.get(sectID);
    }

    /**
     * @return the Map containing the sectors of this game map
     */
    public Map<String, Sector> getBoard() {
        return board;
    }

    public boolean getMapLoaded() {
        return mapLoaded;
    }

}
