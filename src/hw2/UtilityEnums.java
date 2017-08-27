package hw2;


public class UtilityEnums {
	
	public enum Mode {
		MINIMAX, ALPHABETA, COMPETITION
	}
	public enum Player {
		X, O;
		public Player getOpposition(){
			if(this == Player.O){
				return Player.X;
			}
			return Player.O;
		}
	}
	public enum MoveType {
		Stake, Raid
	}

}
