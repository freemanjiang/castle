package castle;

import java.util.HashMap;
import java.util.Scanner;

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

	public Room GetNextRoomByRandom() {
		int i = 0;
		int idx = (int) (Math.random() * exits.size());
		for (Room room : exits.values()) {
			if (i == idx) {
				return room;
			}
			i++;
		}
		System.out.println("failed to get Random door.");
		return null;
	}

	@Override
	public String toString() {
		return description;
	}
}
