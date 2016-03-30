package castle;

import java.util.ArrayList;

public class RoomManager {
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private static RoomManager instance = null;

	private RoomManager() {
	}

	public synchronized static RoomManager getInstance() {
		if (instance == null) {
			instance = new RoomManager();
		}
		return instance;
	}

	public void AddRoom(Room room) {
		rooms.add(room);
	}

	public Room GetRoomByRandom() {
		int idx = (int) (Math.random() * rooms.size());
		return rooms.get(idx);
	}

	public static void main(String[] args) {

	}
}
