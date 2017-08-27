

import java.util.Iterator;
import java.util.LinkedList;

import hw2.UtilityEnums.MoveType;
import hw2.UtilityEnums.Player;

public class Minimax {
	
	protected static final int MAX_VALUE = Integer.MAX_VALUE;
	protected static final int MIN_VALUE = Integer.MIN_VALUE;
	protected Board board;
	protected int depth;
	protected Player myPlayer;
	
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public Minimax(Board board, int depth, Player myPlayer) {
		this.board = board;
		this.depth = depth;
		this.myPlayer = myPlayer;
	}
	
	public BoardState run() {
//		System.out.println("START GAME");
		BoardState maxValueSuccessor = maxValue(board.getInitialBoardState());
		return maxValueSuccessor;
	}
	
	private BoardState maxValue(BoardState currentBoardState) {
		//System.out.println(currentBoardState);
		//System.out.println(player + "'S CHANCE");
		if(currentBoardState.isTerminalState() || currentBoardState.getDepth() == depth){
			//System.out.println("AT DEPTH NOW. RETURNING WITH--");
			currentBoardState.setValue(board.evaluateBoard(myPlayer, currentBoardState));
			//System.out.println(currentBoardState);
			return currentBoardState;
		}
		int v = MIN_VALUE;
		LinkedList<BoardState> successors = getSuccessors(currentBoardState);
		Iterator<BoardState> iterator = successors.iterator();
		BoardState maxValueSuccessor = null;
		while(iterator.hasNext()) {
			BoardState successor = iterator.next();
			BoardState minValueStateOfSuccessor = minValue(successor);
			successor.setValue(minValueStateOfSuccessor.getValue());
			if(v < minValueStateOfSuccessor.getValue()) {
				v = minValueStateOfSuccessor.getValue();
				maxValueSuccessor = successor;
			}
		}
//		System.out.println("MAX VALUE OF ALL:");
//		System.out.println(maxValueSuccessor);
		return maxValueSuccessor;
	}
	
	private BoardState minValue(BoardState currentBoardState) {
		//System.out.println(currentBoardState);
		//System.out.println(player + "'S CHANCE");
		if(currentBoardState.isTerminalState() || currentBoardState.getDepth() == depth){
			//System.out.println("AT DEPTH NOW:"+depth+". RETURNING WITH-");
			currentBoardState.setValue(board.evaluateBoard(myPlayer, currentBoardState));
			//System.out.println(currentBoardState);
			return currentBoardState;
		}
		int v = MAX_VALUE;
		LinkedList<BoardState> successors = getSuccessors(currentBoardState);
		Iterator<BoardState> iterator = successors.iterator();
		BoardState minValueSuccessor = null;
		while(iterator.hasNext()) {
			BoardState successor = iterator.next();
			BoardState maxValueStateOfSuccessor = maxValue(successor);
			successor.setValue(maxValueStateOfSuccessor.getValue());
			if(v > maxValueStateOfSuccessor.getValue()) {
				v = maxValueStateOfSuccessor.getValue();
				minValueSuccessor = successor;
			}
		}
//		System.out.println("MIN VALUE OF ALL:");
//		System.out.println(minValueSuccessor);
		return minValueSuccessor;
	}
	
	protected LinkedList<BoardState> getSuccessors(BoardState currentBoardState) {
		LinkedList<BoardState> successors = new LinkedList<BoardState>();
		addNextDepthStakeBoards(currentBoardState, successors);
		addNextDepthRaidBoards(currentBoardState, successors);
		return successors;
	}
	
	private void addNextDepthStakeBoards(BoardState currentBoardState, LinkedList<BoardState> successors) {
		Player player = getPlayerForThisDepth(currentBoardState.getDepth());
		int n = currentBoardState.getN();
		for(int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++){
				if(currentBoardState.getStateAt(i, j) == null){
					BoardState boardState = new BoardState(currentBoardState, player, i, j);
					boardState.setValue(board.evaluateBoard(myPlayer, boardState));
					boardState.setMoveType(MoveType.Stake);
					successors.addLast(boardState);
				}
			}
		}
	}
	
	private void addNextDepthRaidBoards(BoardState currentBoardState, LinkedList<BoardState> successors) {
		Player player = getPlayerForThisDepth(currentBoardState.getDepth());
		int n = currentBoardState.getN();
		for (int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(currentBoardState.getStateAt(i, j) == null){
					if(canBeRaidedBy(currentBoardState, i, j, player)) {
						BoardState boardState = new BoardState(currentBoardState, player, i, j);
						applyRaidConquers(boardState, player, i, j);
						boardState.setValue(board.evaluateBoard(myPlayer, boardState));
						boardState.setMoveType(MoveType.Raid);
						successors.addLast(boardState);
					}
				}
			}
		}
	}
	
	private void applyRaidConquers(BoardState boardState, Player player, int i, int j) {
		Player opposition = player.getOpposition();
		int n = boardState.getN();
		if(i > 0){
			if(boardState.getStateAt(i - 1, j) == opposition){
				boardState.setStateAt(i - 1, j, player);
			}
		}
		if(j > 0){
			if(boardState.getStateAt(i, j - 1) == opposition){
				boardState.setStateAt(i, j - 1, player);				
			}
		}
		if(i + 1 < n){
			if(boardState.getStateAt(i + 1, j) == opposition){
				boardState.setStateAt(i + 1, j, player);
			}
		}
		if(j + 1 < n){
			if(boardState.getStateAt(i, j + 1) == opposition){
				boardState.setStateAt(i, j + 1, player);
			}
		}
	}
	
	private Player getPlayerForThisDepth(int depth2) {
		if(depth2 % 2 == 0){
			return myPlayer;
		}
		return myPlayer.getOpposition();
	}
	
	private boolean canBeRaidedBy(BoardState boardState, int i, int j, Player player) {
		int n = boardState.getN();
		boolean canBe = false;
		if(i > 0){
			if(boardState.getStateAt(i - 1, j) == player){
				return true;
			}
		}
		if(j > 0){
			if(boardState.getStateAt(i, j - 1) == player){
				return true;
			}
		}
		if(i + 1 < n){
			if(boardState.getStateAt(i + 1, j) == player){
				return true;
			}
		}
		if(j + 1 < n){
			if(boardState.getStateAt(i, j + 1) == player){
				return true;
			}
		}
		return canBe;
	}
}
