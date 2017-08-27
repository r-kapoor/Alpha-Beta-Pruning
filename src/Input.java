

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import hw2.UtilityEnums.Mode;
import hw2.UtilityEnums.Player;

public class Input {
	
	private Mode mode;
	private Player myPlayer;
	private int depth;
	private Board board;

	public Board getBoard() {
		return board;
	}

	public Mode getMode() {
		return mode;
	}

	public Player getMyPlayer() {
		return myPlayer;
	}

	public int getDepth() {
		return depth;
	}

	public Input(int count) throws IOException {
		int n;
		int [][]cellValues;
		Player [][]initialBoardState;
//		File file = new File("input.txt");
		File file = new File("testcases/"+count+".in");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		line = bufferedReader.readLine();
		n = Integer.parseInt(line);
		line = bufferedReader.readLine();
		mode = getModeFromString(line);
		line = bufferedReader.readLine();
		myPlayer = getPlayerFromChar(line.toCharArray()[0]);
		line = bufferedReader.readLine();
		depth = Integer.parseInt(line);
		cellValues = new int[n][n];
		initialBoardState = new Player[n][n];
		for(int i = 0; i < n; i++) {
			line = bufferedReader.readLine();
			String[] cellValuesArray = line.split(" ");
			for (int j = 0; j < n; j++) {
				cellValues[i][j] = Integer.parseInt(cellValuesArray[j]);
			}
		}
		for(int i = 0; i < n; i++) {
			line = bufferedReader.readLine();
			for (int j = 0; j < n; j++) {
				initialBoardState[i][j] = getPlayerFromChar((line.charAt(j)));
			}
		}
		board = new Board();
		board.setN(n);
		board.setCellValues(cellValues);
		BoardState boardState = new BoardState(0, initialBoardState);
		board.setInitialBoardState(boardState);
		boardState.setValue(board.evaluateBoard(getMyPlayer()));
		bufferedReader.close();
	}

	private Mode getModeFromString(String line) {
		if("MINIMAX".equals(line)){
			return Mode.MINIMAX;
		}
		if("ALPHABETA".equals(line)){
			return Mode.ALPHABETA;
		}
		if("COMPETITION".equals(line)) {
			return Mode.COMPETITION;
		}
		return null;
	}
	
	private Player getPlayerFromChar(char line){
		if('X' == line){
			return Player.X;
		}
		if('O' == line){
			return Player.O;
		}
		return null;
	}

}
