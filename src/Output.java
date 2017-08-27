

import java.io.FileWriter;
import java.io.IOException;

public class Output {
	
	BoardState boardState;
	String filename;
	
	public Output(BoardState boardState) {
		this.boardState = boardState;
		filename = "output.txt";
	}
	
	public Output(BoardState boardState, String filename){
		this.boardState = boardState;
		this.filename = filename;
	}
	
	public void write() throws IOException{
		FileWriter fw = new FileWriter(filename);
		int n = boardState.getN();
		fw.write(boardState.getLastMove() + " " + boardState.getMoveType() + "\n");
		for(int i = 0; i < n; i++){ 
			for(int j = 0; j < n; j++){
				if(boardState.getStateAt(i, j) != null){
					fw.write(boardState.getStateAt(i, j).toString());
				}
				else{
					fw.write(".");
				}
			}
			fw.write("\n");
		}
		fw.close();
	}

}
