package castle;

import java.util.HashMap;
import java.util.Scanner;

class Userstate {
	public Room currentRoom;
	public HashMap<String, UserCmd> ucmds;
}

interface UserCmd {
	public boolean DoUserCmd(String[] words, Userstate us);

	public boolean IsBye();
}

class GetHelp implements UserCmd {
	@Override
	public boolean DoUserCmd(String[] words, Userstate us) {
		System.out.print("迷路了吗？你可以做的命令有：");
		for (String cmd : us.ucmds.keySet()) {
			System.out.print(cmd + " ");
		}
		System.out.println("如：\tgo east");
		return true;
	}

	public boolean IsBye() {
		return false;
	}
}

class LookAround implements UserCmd {

	@Override
	public boolean DoUserCmd(String[] words, Userstate us) {
		System.out.println("观察了一下周围");
		return true;
	}

	public boolean IsBye() {
		return false;
	}
}

class GoRoom implements UserCmd {
	@Override
	public boolean DoUserCmd(String[] words, Userstate us) {
		try {
			String direction = words[1];

			Room ret = us.currentRoom.GetNextRoomByDirection(direction);
			if (ret != null) {
				us.currentRoom = ret;
			} else {
				System.out.println("那里没有门！");
			}
			return true;
		} catch (Exception e) {
			System.out.println("GoRom命令错误");
			return false;
		}
	}

	public boolean IsBye() {
		return false;
	}
}

class Bye implements UserCmd {
	@Override
	public boolean DoUserCmd(String[] words, Userstate us) {
		System.out.println("感谢您的光临。再见！");
		return true;
	}

	public boolean IsBye() {
		return true;
	}
}

public class Game {
	private static Userstate us = new Userstate();
	// private Room currentRoom;
	private HashMap<String, UserCmd> ucmds = new HashMap<String, UserCmd>();

	public Game() {
		createRooms();
		createUserCmds();
	}

	private void createUserCmds() {
		ucmds.put("help", new GetHelp());
		ucmds.put("go", new GoRoom());
		ucmds.put("bye", new Bye());
		ucmds.put("look", new LookAround());
	}

	private void createRooms() {
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

		us.currentRoom = outside; // 从城堡门外开始
		us.ucmds = ucmds;
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
