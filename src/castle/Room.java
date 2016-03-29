package castle;

import java.util.HashMap;

public class Room {
	private String description;

	private HashMap<String, Room> exits = new HashMap<String, Room>();

	public Room(String description) {
		this.description = description;
	}

	public void addExit(String direction, Room rm) {
		exits.put(direction, rm);
	}

	public void ShowExits() {
		for (String exit : exits.keySet()) {
			System.out.print(exit + " ");
		}
	}

	public boolean CanIGoThisDiection(String direction)
	{
		for (String exit : exits.keySet()) {
			if (exit.equals(direction)) {
				return true;
			}
		}
		return true;
	}
	
	public Room GetNextRoomByDirection(String dirction)
	{
		try{
			return exits.get(dirction);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	@Override
	public String toString() {
		return description;
	}
}
