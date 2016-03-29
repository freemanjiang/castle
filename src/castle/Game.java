package castle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Userstate {
	public Room currentRoom;
	public HashMap<String, UserCmd> ucmds;
}

class UserCmd {
	protected Game game;

	public UserCmd(Game game) {
		this.game = game;
	}

	public void DoUserCmd(String[] words, Userstate us) {
	};

	public boolean IsBye() {
		return false;
	};
}

class GetHelp extends UserCmd {
	public GetHelp(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void DoUserCmd(String[] words, Userstate us) {
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

	public LookAround(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void DoUserCmd(String[] words, Userstate us) {
		System.out.println("观察了一下周围");
	}

	@Override
	public boolean IsBye() {
		return false;
	}
}

class GoRoom extends UserCmd {
	public GoRoom(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void DoUserCmd(String[] words, Userstate us) {
		try {
			String direction = words[1];
			Room ret = null;
			if (direction.equals("randdoor")) {
				ret = us.currentRoom.GetNextRoomByRandom();
			} else if (direction.equals("randroom")) {
				ret = game.GetRoomByRandom();
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
	public Bye(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void DoUserCmd(String[] words, Userstate us) {
		System.out.println("感谢您的光临。再见！");
	}

	@Override
	public boolean IsBye() {
		return true;
	}
}

public class Game {
	private Userstate us = new Userstate();
	// private Room currentRoom;
	public HashMap<String, UserCmd> ucmds = new HashMap<String, UserCmd>();
	private ArrayList<Room> rooms = new ArrayList<Room>();

	public Game() {
		Room startingPoint = createRooms();
		createUserCmds();
		InitializeUserstate(startingPoint);
	}

	private void InitializeUserstate(Room startingPoing) {
		us.currentRoom = startingPoing;
		us.ucmds = ucmds;
	}

	private void createUserCmds() {
		ucmds.put("help", new GetHelp(this));
		ucmds.put("go", new GoRoom(this));
		ucmds.put("bye", new Bye(this));
		ucmds.put("look", new LookAround(this));
	}

	private Room createRooms() {
		Room outside, lobby, pub, study, bedroom, playground;

		// 制造房间
		outside = new Room("城堡外");
		lobby = new Room("大堂");
		pub = new Room("小酒吧");
		study = new Room("书房");
		bedroom = new Room("卧室");
		playground = new Room("游乐场");

		rooms.add(outside);
		rooms.add(lobby);
		rooms.add(pub);
		rooms.add(study);
		rooms.add(bedroom);
		rooms.add(playground);

		// 初始化房间的出口
		outside.addExit("east", lobby);
		outside.addExit("south", study);
		outside.addExit("west", pub);
		outside.addExit("northeast", playground);
		lobby.addExit("west", outside);
		pub.addExit("east", outside);
		study.addExit("north", outside);
		study.addExit("east", bedroom);
		bedroom.addExit("west", study);
		playground.addExit("southwest", outside);

		return outside;// 从城堡门外开始
	}

	public Room GetRoomByRandom() {
		int idx = (int) (Math.random() * rooms.size());
		return rooms.get(idx);
	}

	private void showState() {
		System.out.println("现在你在" + us.currentRoom);
		System.out.print("出口有：");
		us.currentRoom.ShowExits();
		System.out.println();
	}

	private void Play() {
		Scanner in = new Scanner(System.in);
		while (true) {
			String line = in.nextLine();
			String[] words = line.split(" ");
			UserCmd uc = ucmds.get(words[0]);
			if (uc != null) {
				uc.DoUserCmd(words, us);
				if (uc.IsBye()) {
					break;
				}
				showState();
			}
		}
		in.close();
	}

	private void printWelcome() {
		System.out.println();
		System.out.println("欢迎来到城堡！");
		System.out.println("这是一个超级无聊的游戏。");
		System.out.println("如果需要帮助，请输入 'help' 。");
		System.out.println();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.printWelcome();
		game.showState();
		game.Play();
	}
}
