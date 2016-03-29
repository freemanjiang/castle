package castle;

import java.util.HashMap;

public class Room {
    private String description;

    public HashMap<String, Room> exits = new  HashMap<String, Room>();    
    
    public Room(String description) 
    {
        this.description = description;
    }
    
    public void addExit(String direction,Room rm)
    {
        exits.put(direction, rm);
    }

    @Override
    public String toString()
    {
        return description;
    }
}

