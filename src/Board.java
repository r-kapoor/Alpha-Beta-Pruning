import hw2.UtilityEnums.Player;

public class Board {
	
	private int n;
	private int [][]cellValues;
	private BoardState initialBoardState;
	
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	
	public int[][] getCellValues() {
		return cellValues;
	}
	public void setCellValues(int[][] cellValues) {
		this.cellValues = cellValues;
	}
	
	public BoardState getInitialBoardState() {
		return initialBoardState;
	}
	public void setInitialBoardState(BoardState initialBoardState) {
		this.initialBoardState = initialBoardState;
	}
	public String toString() {
		String text = "";
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				text += cellValues[i][j] + " ";
			}
			text += "\n";
		}
		return text + initialBoardState.toString();
	}
	
	public int evaluateBoard(Player player){
		return evaluateBoard(player, initialBoardState);
	}
	
	public int evaluateBoard(Player player, BoardState boardState){
		int scoreForX = 0;
		int scoreForO = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(boardState.getStateAt(i, j) == Player.X){
					scoreForX += cellValues[i][j];
				}
				else if(boardState.getStateAt(i, j) == Player.O){
					scoreForO += cellValues[i][j];
				}
			}
		}
		if(player == Player.X){
			return scoreForX - scoreForO;
		}
		return scoreForO - scoreForX;
	}

}
