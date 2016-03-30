package castle;

public class UserCmd {

	protected Game game;
	protected RoomManager roommanager;

	public UserCmd(RoomManager roommanager) {
		this.roommanager = roommanager;
	}

	public void DoUserCmd(String[] words, PlayerState us) {
	};

	public boolean IsBye() {
		return false;
	};
}

class GetHelp extends UserCmd {
	public GetHelp(RoomManager roommanager) {
		super(roommanager);
	}

	@Override
	public void DoUserCmd(String[] words, PlayerState us) {
		System.out.print("迷路了吗？你可以做的命令有：");
		for (String cmd : us.ucmds.keySet()) {
			System.out.print(cmd + " ");
		}
		System.out.println("如：\tgo east");
	}

	@Override
	public boolean IsBye() {
		return false;
	}
}

class LookAround extends UserCmd {

	public LookAround(RoomManager roommanager) {
		super(roommanager);
	}

	@Override
	public void DoUserCmd(String[] words, PlayerState us) {
		System.out.println("观察了一下周围");
	}

	@Override
	public boolean IsBye() {
		return false;
	}
}

class GoRoom extends UserCmd {
	public GoRoom(RoomManager roommanager) {
		super(roommanager);
	}

	@Override
	public void DoUserCmd(String[] words, PlayerState us) {
		try {
			String direction = words[1];
			Room ret = null;
			if (direction.equals("randdoor")) {
				ret = us.currentRoom.GetNextRoomByRandom();
			} else if (direction.equals("randroom")) {
				ret = roommanager.GetRoomByRandom();

			} else {
				ret = us.currentRoom.GetNextRoomByDirection(direction);
			}
			if (ret != null) {
				us.currentRoom = ret;
			} else {
				System.out.println("那里没有门！");
			}
		} catch (Exception e) {
			System.out.println("GoRom命令错误");
		}
	}

	@Override
	public boolean IsBye() {
		return false;
	}
}

class Bye extends UserCmd {
	public Bye(RoomManager roommanager) {
		super(roommanager);
	}

	@Override
	public void DoUserCmd(String[] words, PlayerState us) {
		System.out.println("感谢您的光临。再见！");
	}

	@Override
	public boolean IsBye() {
		return true;
	}
}
