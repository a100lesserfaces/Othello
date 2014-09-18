/*
 * Name:	Alexandria BenDebba 
 * UID: 	112366018 
 * Section: 0303
 */

package othello;

import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

public class Othello implements Comparable<Othello> {

	public Piece[][] gameBoard=new Piece [8][8];
	//Two-dimensional array to hold the spaces on the board
	Piece nextTurn;
	//Piece object that holds the color of the next turn

	public Othello() {
		/*
		 * Creates a new Othello object with an empty board (all Pieces are set
		 * to NONE) and the next turn is black.
		 */
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[i].length; j++){
				//Fills in all of the spaces
				gameBoard[i][j]=Piece.NONE;
			}
		}
		nextTurn = Piece.BLACK;
	}

	public Othello(Othello otherGame) {
		/*
		 * Creates a new Othello object using the information from another one,
		 * using a deep copy that copies each individual space on the board,
		 * and has the same Piece color as next turn.
		 */
		this.nextTurn = otherGame.nextTurn;
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[i].length; j++){
				gameBoard[i][j]=otherGame.gameBoard[i][j];
			}
		}
	}

	public void restart(Piece color) throws IllegalArgumentException {
		/*
		 * Restarts the Othello board with white pieces at 3,3 and 4,4 and black
		 * pieces at 3,4 and 4,3.  If a Piece.NONE is passed as parameter,
		 * the method throws an IllegalArgumentException.  Otherwise, next turn
		 * is set to the parameter.
		 */
		Othello temp = new Othello();
		if(color == Piece.NONE){
			throw new IllegalArgumentException();
		}else{
			this.nextTurn = color;
		}

		this.gameBoard=temp.gameBoard;
		this.setEntry(Piece.WHITE, 3, 3);
		this.setEntry(Piece.WHITE, 4, 4);
		this.setEntry(Piece.BLACK, 3, 4);
		this.setEntry(Piece.BLACK, 4, 3);
	}

	public void setTurn(Piece color) throws IllegalArgumentException {
		/*
		 * Sets the turn to the parameter.  If Piece.NONE is passed, this 
		 * method throws an IllegalArgumentException.
		 */
		if(color == Piece.NONE){
			throw new IllegalArgumentException();
		}else{
			this.nextTurn = color;
		}
	}

	public Piece getTurn() {
		/*
		 * Returns the color of the next turn.
		 */
		return this.nextTurn;
	}

	public void setEntry(Piece color, int row, int col)
			throws NoSuchElementException {
		/*
		 * Sets a piece on the row, col space on the board.  If the row or col
		 * is out of the range of the board, it throws a NoSuchElementException 
		 */
		if(row > 7 || row < 0 || col > 7 || col < 0){
			throw new NoSuchElementException();
		}else{
			gameBoard[row][col] = color;
		}
	}

	public Piece getEntry(int row, int col) throws NoSuchElementException {
		/*
		 * Returns the Piece at the row, col space on the board.  If the row or
		 * col is out of the range of the board, it throws a 
		 * NoSuchElementException.
		 */
		if(row > 7 || row < 0 || col > 7 || col < 0){
			throw new NoSuchElementException();
		}else{
			return gameBoard[row][col];
		}
	}
	
	private boolean isEmptyArea(int row, int col){
		int startRow = ((row - 1) >= 0) ? row - 1: row;
		int endRow = ((row + 1) <= 7) ? row + 1: row;
		int startCol = ((col - 1) >= 0) ? col - 1: col;
		int endCol = ((col + 1) <= 7) ? col + 1: col;
		for(int r = startRow; r <= endRow; r++){
			for(int c = startCol; c <= endCol; c++){
				if(gameBoard[r][c].toChar() != Piece.NONE.toChar())
					return false;
			}
		}
		return true;
	}
	
	public boolean validMove(Piece color, int row, int col) {
		/*
		 * Tests to see if the player represented by color could play in the
		 * row, col space on the board.  Returns true if the space is open,
		 * valid on the board, and would trap and flip an enemy piece.  Returns
		 * false otherwise.
		 */
		if(row > 7 || row < 0 || col > 7 || col < 0){
			return false;
		}else if(gameBoard[row][col].toChar() != Piece.NONE.toChar()){
			return false;
		}
		
		if(isEmptyArea(row, col))
			return false;
		
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[i].length; j++){
				if(this.wouldFlip(i, j, row, col))
					return true;
			}
		}
		return false;
	}

	public boolean wouldFlip(int row, int col, int moveRowPos, int moveColPos) {
		/*
		 * Returns true if the position specified at row, col would flip if a
		 * piece of the enemy's color is placed at moveRowPos, moveColPos.  
		 * Does not actually flip the piece, only returns true or false
		 */
		
		//This checks for invalid row, col, moveRowPos, or moveColPos
		if(moveRowPos > 7 || moveRowPos < 0 || moveColPos > 7 
				|| moveColPos < 0){
			return false;
		}else if(row > 7 || row < 0 || col > 7 
				|| col < 0){
			return false;
		}else if(gameBoard[row][col].toChar() == nextTurn.toChar()){
			return false;
		}//Checks to see if the pieces are the same color

		if(moveRowPos == row && moveColPos > col){//move on the right
			for(int i = col-1; i >= 0; i--){
				if(gameBoard[row][i].toChar() == nextTurn.toChar()){
					return true;
				}
			}
		}else if(moveRowPos == row && moveColPos < col){//move on left
			for(int i = col+1; i <= 7; i++){
				if(gameBoard[row][i].toChar() == nextTurn.toChar()){
					return true;
				}
			}
		}else if(moveColPos == col && moveRowPos > row){//move on down
			for(int i = row -1; i >= 0 ; i--){
				if(gameBoard[i][col].toChar() == nextTurn.toChar()){
					return true;
				}
			}
		}else if(moveColPos == col && moveRowPos < row){//move on up
			int i = row+1;
			while(i <= 7 && gameBoard[i][col].toChar() != Piece.NONE.toChar()){
				if(gameBoard[i][col].toChar() == nextTurn.toChar()){
					return true;
				}
				i++;
			}
		}else if(moveColPos > col && moveRowPos > row){//lower-right
			int nRow = row - 1;
			int nCol = col - 1;
			while(nRow >= 0 && nCol >= 0 && 
					gameBoard[nRow][nCol] != Piece.NONE){
				if(gameBoard[nRow][nCol].toChar() == nextTurn.toChar()){
					return true;
				}
				nRow--;
				nCol--;
			}
		}else if(moveColPos > col && moveRowPos < row){//upper-right
			int nRow = row + 1;
			int nCol = col - 1;
			while(nRow <= 7 && nCol >= 0 && 
					gameBoard[nRow][nCol] != Piece.NONE){
				if(gameBoard[nRow][nCol].toChar() == nextTurn.toChar()){
					return true;
				}
				nRow++;
				nCol--;
			}
		}else if(moveColPos < col && moveRowPos > row){//lower-left
			int nRow = row - 1;
			int nCol = col + 1;
			while(nRow >= 0 && nCol <= 7 && 
					gameBoard[nRow][nCol] != Piece.NONE){
				if(gameBoard[nRow][nCol].toChar() == nextTurn.toChar()){
					return true;
				}
				nRow--;
				nCol++;
			}
		}else if(moveColPos < col && moveRowPos < row){//upper-left
			int nRow = row + 1;
			int nCol = col + 1;
			while(nRow < 8 && nCol < 8 && 
					gameBoard[nRow][nCol] != Piece.NONE){
				if(gameBoard[nRow][nCol].toChar() == nextTurn.toChar()){
					return true;
				}
				nRow++;
				nCol++;
			}
		}

		return false;
	}

	public void flip(Piece newColor, int row, int col){
		/*
		 * Flips the piece to the newColor (so long as newColor does not equal
		 * Piece.NONE at the space row, col
		 */
		if(this.gameBoard[row][col].toChar() != newColor.toChar()
				&& this.gameBoard[row][col].toChar() != Piece.NONE.toChar()){
			this.gameBoard[row][col] = newColor;
		}
	}

	private void flipMove(int row, int col){
		/*
		 * Makes all of the flips for a move turn
		 */
		//move on the right
		for(int i = col-1; i >= 0; i--){
			if(this.wouldFlip(row, i, row, col)){
				this.flip(nextTurn, row, i);
			}
		}
		//move on left
		for(int i = col+1; i <= 7; i++){
			if(this.wouldFlip(row, i, row, col)){
				this.flip(nextTurn, row, i);
			}
		}
		//move on down
		for(int i = row -1; i >= 0 ; i--){
			if(this.wouldFlip(i, col, row, col)){
				this.flip(nextTurn, i, col);
			}
		}
		//move on up
		for(int i = row+1; i <= 7; i++){
			if(this.wouldFlip(i, col, row, col)){
				this.flip(nextTurn, i, col);
			}
		}
		//lower-right
		int nRow = row - 1;
		int nCol = col - 1;
		while(nRow >= 0 && nCol >= 0){
			if(this.wouldFlip(nRow, nCol, row, col)){
				this.flip(nextTurn, nRow, nCol);
			}
			nRow--;
			nCol--;
		}
		//upper-right
		nRow = row + 1;
		nCol = col - 1;
		while(nRow <= 7 && nCol >= 0){
			if(this.wouldFlip(nRow, nCol, row, col)){
				this.flip(nextTurn, nRow, nCol);
			}
			nRow++;
			nCol--;
		}
		//lower-left
		nRow = row - 1;
		nCol = col + 1;
		while(nRow >= 0 && nCol <= 7){
			if(this.wouldFlip(nRow, nCol, row, col)){
				this.flip(nextTurn, nRow, nCol);
			}
			nRow--;
			nCol++;
		}
		//upper-left
		nRow = row + 1;
		nCol = col + 1;
		while(nRow < 8 && nCol < 8){
			if(this.wouldFlip(nRow, nCol, row, col)){
				this.flip(nextTurn, nRow, nCol);
			}
			nRow++;
			nCol++;

		}
	}
	public void move(int row, int col) {
		/*
		 * Tests to first see if the nextTurn player could make a move at the
		 * designated row, col space.  If it can, this method flips the 
		 * necessary enemy pieces, places a new piece down at row, col, and 
		 */
		if(this.validMove(nextTurn, row, col)){
			this.flipMove(row, col);
			this.setEntry(nextTurn, row, col);
			//Changes the turn to the next color
			if(nextTurn.toChar() == Piece.BLACK.toChar()){
				nextTurn = Piece.WHITE;
			}else{
				nextTurn = Piece.BLACK;
			}
		}

	}

	public void move(Piece color, int row, int col) {
		/*
		 * Tests to make sure color is equal to nextTurn, and then performs
		 * the move(int, int) method.
		 */
		if(color.toChar() != nextTurn.toChar()){
			return;
		}
		if(!this.validMove(nextTurn, row, col)){
			return;
		}
		this.move(row, col);
	}

	public int count(Piece color) {
		/*
		 * Returns the number of pieces matching the color of the parameter
		 */
		int count = 0;
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[i].length; j++){
				if(color == gameBoard[i][j]){
					count++;
				}
			}
		}
		return count;
	}

	public Piece winning() {
		/*
		 * Counts all of the pieces and returns the team with more pieces on
		 * the board
		 */
		if(count(Piece.BLACK) > count(Piece.WHITE)){
			return Piece.BLACK;
		}else if(count(Piece.BLACK) < count(Piece.WHITE)){
			return Piece.WHITE;
		}else{
			return Piece.NONE;
		}
	}

	public int compareTo(Othello otherGame) {
		/*
		 * Compares the current Othello object and the object denoted by 
		 * otherGame.  Returns 0 if the two games are exactly the same (same
		 * number of both colored Pieces, same nextTurn).  Returns 1 if BLACK
		 * is winning in the current game, returns -1 if WHIE is winning in the
		 * current game.
		 */
		boolean areEqual=true;
		for(int i = 0; i < gameBoard.length; i++){
			for(int j = 0; j < gameBoard[i].length; j++){
				if(gameBoard[i][j] !=otherGame.gameBoard[i][j]){
					areEqual=false;
				}
			}
		}
		//Will only return a 0 if first all of the spaces in gameBoard have
		//the same value
		if(areEqual && this.nextTurn.toChar() == otherGame.nextTurn.toChar()){
			return 0;
		}
		//If not, it compares the number of BLACK pieces to WHITE pieces
		//in the current game
		if(this.winning().toChar() == Piece.BLACK.toChar()){
			return 1;
		}else if(this.winning().toChar() == Piece.WHITE.toChar()){
			return -1;
		}else{
			return 0;
		}
	}

}
