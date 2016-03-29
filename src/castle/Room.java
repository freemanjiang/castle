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

	public Room GetNextRoomByDirection(String dirction) {
		return exits.get(dirction);
	}

	@Override
	public String toString() {
		return description;
	}
}
