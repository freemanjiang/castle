package castle;

public class UserCmd {

	protected Game game;
	protected RoomManager roommanager;

	public UserCmd(RoomManager roommanager) {
		this.roommanager = roommanager;
	}

	public void DoUserCmd(String[] words, Userstate us) {
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
	public void DoUserCmd(String[] words, Userstate us) {
		System.out.print("��·������������������У�");
		for (String cmd : us.ucmds.keySet()) {
			System.out.print(cmd + " ");
		}
		System.out.println("�磺\tgo east");
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
	public void DoUserCmd(String[] words, Userstate us) {
		System.out.println("�۲���һ����Χ");
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
	public void DoUserCmd(String[] words, Userstate us) {
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
				System.out.println("����û���ţ�");
			}
		} catch (Exception e) {
			System.out.println("GoRom�������");
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
	public void DoUserCmd(String[] words, Userstate us) {
		System.out.println("��л���Ĺ��١��ټ���");
	}

	@Override
	public boolean IsBye() {
		return true;
	}
}
