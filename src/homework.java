

import java.io.IOException;

import hw2.UtilityEnums.Mode;

public class homework {
	
	public static void main(String args[]) throws IOException {
		for (int i=0; i < 100; i++){
			System.out.println(i);
			Input input = new Input(i);
			Board board = input.getBoard();
//			System.out.println(board);
			BoardState nextState;
			if(input.getMode() == Mode.MINIMAX){
				Minimax minimax = new Minimax(board, input.getDepth(), input.getMyPlayer());
				nextState = minimax.run();
			}
			else {
				Minimax alphabeta = new AlphaBeta(board, input.getDepth(), input.getMyPlayer());
				nextState = alphabeta.run();
			}
//			System.out.println("MY MOVE::");
//			System.out.println(nextState);
			Output output = new Output(nextState);
			nextState.match(i);
			output.write();
		}
	}
}
