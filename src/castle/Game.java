package castle;

import java.util.HashMap;
import java.util.Scanner;

class PlayerState {
	public Room currentRoom;
	public HashMap<String, UserCmd> ucmds;
}

public class Game {
	private PlayerState playerstate = new PlayerState();
	private RoomManager roommanager = RoomManager.getInstance();
	public HashMap<String, UserCmd> ucmds = new HashMap<String, UserCmd>();

	public Game() {
		Room startingPoint = createRooms();
		createUserCmds();
		InitializeUserstate(startingPoint);
	}

	private void InitializeUserstate(Room startingPoing) {
		playerstate.currentRoom = startingPoing;
		playerstate.ucmds = ucmds;
	}

	private void createUserCmds() {
		ucmds.put("help", new GetHelp(roommanager));
		ucmds.put("go", new GoRoom(roommanager));
		ucmds.put("bye", new Bye(roommanager));
		ucmds.put("look", new LookAround(roommanager));
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

		roommanager.AddRoom(outside);
		roommanager.AddRoom(lobby);
		roommanager.AddRoom(pub);
		roommanager.AddRoom(study);
		roommanager.AddRoom(bedroom);
		roommanager.AddRoom(playground);

		return outside;// 从城堡门外开始
	}

	private void showState() {
		System.out.println("现在你在" + playerstate.currentRoom);
		System.out.print("出口有：");
		playerstate.currentRoom.ShowExits();
		System.out.println();
	}

	private void Play() {
		Scanner in = new Scanner(System.in);
		while (true) {
			String line = in.nextLine();
			String[] words = line.split(" ");
			UserCmd uc = ucmds.get(words[0]);
			if (uc != null) {
				uc.DoUserCmd(words, playerstate);
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
