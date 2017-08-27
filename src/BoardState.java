

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import hw2.UtilityEnums.MoveType;
import hw2.UtilityEnums.Player;

public class BoardState {
	private int n;
	private int depth;
	private Player [][]boardState;
	private int value;
	private MoveType moveType;
	private String lastMove;

	public String getLastMove() {
		return lastMove;
	}

	public void setLastMove(String lastMove) {
		this.lastMove = lastMove;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public Player[][] getBoardState() {
		return boardState;
	}

	public void setBoardState(Player[][] boardState) {
		this.boardState = boardState;
	}
	
	public Player getStateAt(int i, int j){
		return boardState[i][j];
	}
	
	public void setStateAt(int i, int j, Player player){
		boardState[i][j] = player;
	}
	
	public BoardState(int depth, Player[][] boardState){
		this.depth = depth;
		this.boardState = boardState;
		this.n = boardState.length;
	}
	
	public BoardState(BoardState currentBoardState, Player playerToBePlaced, int locationI, int locationJ){
		n = currentBoardState.getN();
		boardState = new Player[n][n];
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				if(i == locationI && j == locationJ){
					boardState[i][j] = playerToBePlaced;
				}
				else {
					boardState[i][j] = currentBoardState.getStateAt(i, j);
				}
			}
		}
		depth = currentBoardState.getDepth() + 1;
		lastMove = getMoveString(locationI, locationJ);
	}

	private String getMoveString(int locationI, int locationJ) {
		return (""+(char)('A'+(locationJ))) + (locationI+1);
	}
	
	

	public String toString() {
		String text = "VALUE:"+value + "\n";
		text += lastMove + " " + moveType + "\n";
		for(int i = 0; i < n; i++){ 
			for(int j = 0; j < n; j++){
				if(boardState[i][j] != null){
					text += boardState[i][j];
				}
				else{
					text += ".";
				}
				if(j != n-1){
					 text += " ";
				}
			}
			text += "\n";
		}
		return text;
	}

	public boolean isTerminalState() {
		boolean isTerminal = true;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(boardState[i][j] == null){
					return false;
				}
			}
		}
//		System.out.println("IS TERMINAL STATE");
		return isTerminal;
	}

	public void match(int count) throws IOException {
		File file = new File("testcases/"+count+".out");
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		line = bufferedReader.readLine();
		String[] lineSplit = line.split(" ");
		if(!(lineSplit[0].equals(this.getLastMove()) && lineSplit[1].equals(this.getMoveType().toString()))){
			System.out.println("ERROR:"+count);
		}
		bufferedReader.close();
	}
}
