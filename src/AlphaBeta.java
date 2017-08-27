

import java.util.Iterator;
import java.util.LinkedList;

import hw2.UtilityEnums.Player;


public class AlphaBeta extends Minimax {

	public AlphaBeta(Board board, int depth, Player myPlayer) {
		super(board, depth, myPlayer);
	}

	@Override
	public BoardState run(){
//		System.out.println("START GAME");
		BoardState maxValueSuccessor = maxValue(board.getInitialBoardState(), MIN_VALUE, MAX_VALUE);
//		System.out.println("ALPHA BETA");
		return maxValueSuccessor;
	}

	private BoardState maxValue(BoardState currentBoardState, int alpha, int beta){
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
			BoardState minValueStateOfSuccessor = minValue(successor, alpha, beta);
			successor.setValue(minValueStateOfSuccessor.getValue());
			if(v < minValueStateOfSuccessor.getValue()) {
				v = minValueStateOfSuccessor.getValue();
				maxValueSuccessor = successor;
				if(v >= beta) {
					return maxValueSuccessor;
				}
			}
			alpha = Integer.max(alpha, v);
		}
		//System.out.println("MAX VALUE OF ALL:");
		//System.out.println(maxValueSuccessor);
		return maxValueSuccessor;
	}
	
	private BoardState minValue(BoardState currentBoardState, int alpha, int beta) {
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
			BoardState maxValueStateOfSuccessor = maxValue(successor, alpha, beta);
			successor.setValue(maxValueStateOfSuccessor.getValue());
			if(v > maxValueStateOfSuccessor.getValue()) {
				v = maxValueStateOfSuccessor.getValue();
				minValueSuccessor = successor;
				if(v <= alpha){
					return minValueSuccessor;
				}
				beta = Integer.min(beta, v);
			}
		}
		//System.out.println("MIN VALUE OF ALL:");
		//System.out.println(minValueSuccessor);
		return minValueSuccessor;
	}

}
