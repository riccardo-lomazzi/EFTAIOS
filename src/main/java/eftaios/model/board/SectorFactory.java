package eftaios.model.board;

public class SectorFactory {
    
    private SectorFactory() {
    }
    
    /**
     * Create a sector from a compressed version inside a string
     * @param sectorAsString compressed version of a sector
     * @return returns the sector decoded from the string 
     */
    public static Sector createSector(String sectorAsString) {
        
        String sectorType = generateSectorType(sectorAsString);
        Character charID = generateCharID(sectorAsString);
        Integer intID = generateIntID(sectorAsString);
        
        switch(sectorType.toUpperCase()){
            case "WALL":
                        return new WallSector(charID,intID);
            case "DANG":
                        return new DangerousSector(charID,intID);
            case "SAFE":
                        return new SafeSector(charID,intID);
            case "ESCP":
                        return new EscapePodSector(charID,intID);
            case "HUMS":
                        return new HumanStartingSector(charID,intID);
            case "ALIS":
                        return new AlienStartingSector(charID,intID);
            default://we choose as default a wall sector 
                    return new WallSector(charID,intID);
        }
    }

    /*
     * The following methods decode the string given to the factory
     * the given string must be in our compressed form 
     * i.e.:A06wall A13safe
     */
    
    private static String generateSectorType(String sectorAsString) {
       return sectorAsString.substring(3, sectorAsString.length());   
    }

    private static Character generateCharID(String sectorAsString) {
        return sectorAsString.charAt(0);
    }
    
    private static Integer generateIntID(String sectorAsString) {
        return Integer.parseInt(sectorAsString.substring(1, 3));
    }

}
