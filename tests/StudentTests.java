package tests;

import org.junit.*;

import othello.Othello;
import othello.Piece;
import static org.junit.Assert.*;

public class StudentTests {

  @Test public void testCompareTo(){
	  Othello o1 = new Othello();
	  Othello o2 = new Othello();
	  o1.setEntry(Piece.BLACK, 0, 0);
	  o1.setTurn(Piece.WHITE);
	  Othello o3 = new Othello (o1);
	  
	  assertEquals(o1.compareTo(o2), 1);
	  assertEquals(o1.compareTo(o3), 0);
	  assertEquals(o2.compareTo(o1), 0);
  }
  @Test public void testCopyConstructor(){
	  Othello o1 = sample1();
	  Othello o2 = new Othello(o1);
	  for(int i = 0; i< o1.gameBoard.length; i++){
		  for(int j = 0; j < o1.gameBoard[i].length; j++){
			  assertEquals(o1.gameBoard[i][j].toChar(),
					  o2.gameBoard[i][j].toChar());
		  }
	  }
  }
  @Test(expected = IllegalArgumentException.class) public void testRestart(){
	  Othello o1 = sample1();
	  o1.restart(Piece.NONE);
  }
  @Test(expected = IllegalArgumentException.class) public void testSetTurn(){
	  Othello o1 = sample1();
	  o1.setTurn(Piece.NONE);
  }
  @Test public void testSetEntry() {
	  Othello o1 = sample1();
	    try {
	        o1.setEntry(Piece.BLACK, 8, 0);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	        o1.setEntry(Piece.BLACK, -1, 0);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }try {
	        o1.setEntry(Piece.BLACK, 0, 8);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	        o1.setEntry(Piece.BLACK, 0, -1);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	        o1.setEntry(Piece.BLACK, 8, 8);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	        o1.setEntry(Piece.BLACK, -1, -1);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	}
  @Test public void testGetEntry() {
	  Othello o1 = sample1();
	    try {
	    	o1.getEntry(8, 0);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	        o1.getEntry(-1, 0);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }try {
	    	o1.getEntry(0, 8);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	    	o1.getEntry(0, -1);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	    	o1.getEntry(8, 8);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	    try {
	    	o1.getEntry(-1, -1);
	    } catch (java.util.NoSuchElementException e) {
	        assertTrue(true);
	    }
	}
  @Test public void testWouldFlip(){
	  Othello o1 = sample1();
	  o1.setEntry(Piece.WHITE, 0, 1);
	  assertTrue(o1.wouldFlip(1, 1, 2, 1));//up - down
	  assertFalse(o1.wouldFlip(1, 1, 1, 2));
	  
	  Othello o2 = sample1();
	  o2.setEntry(Piece.WHITE, 0, 0);
	  assertTrue(o2.wouldFlip(1, 1, 2, 2));//up-left - down-right
	  assertFalse(o2.wouldFlip(1, 1, 2, 1));
	  
	  Othello o3 = sample1();
	  o3.setEntry(Piece.WHITE, 2, 0);
	  assertTrue(o3.wouldFlip(1, 1, 0, 2));//down-left - up-right
	  
	  assertFalse(o3.wouldFlip(1, 1, 8, 2));
	  assertFalse(o3.wouldFlip(1, 1, -1, 2));
	  assertFalse(o3.wouldFlip(1, 1, 0, 8));
	  assertFalse(o3.wouldFlip(1, 1, 0, -1));
	  
	  
	  Othello o4 = sample1();
	  o4.restart(Piece.WHITE);
	  o4.move(5, 3);
	  assertTrue(o4.getEntry(5,3).toChar() == Piece.WHITE.toChar());
	  
  }
  @Test public void testValidMove(){
	  Othello o1 = sample1();
	  assertFalse(o1.validMove(Piece.BLACK, 8, 8));
	  assertFalse(o1.validMove(Piece.BLACK, 1, 1));
	  
	  o1.setEntry(Piece.WHITE, 0, 1);
	  assertTrue(o1.validMove(Piece.WHITE, 2, 1));
	  o1.setEntry(Piece.WHITE, 0, 2);
	  assertTrue(o1.wouldFlip(1, 1, 2, 0));
	  assertTrue(o1.validMove(Piece.WHITE, 2, 0));
  }
  
  @Test public void testFlip(){
	  Othello o1 = sample1();
	  o1.flip(Piece.WHITE, 1, 1);
	  assertEquals(o1.getEntry(1, 1).toChar(), Piece.WHITE.toChar());
  }
  @Test public void testMove(){
	  Othello o1 = new Othello();
	  o1.restart(Piece.BLACK);
	  assertTrue(o1.validMove(Piece.BLACK, 5, 4));
	  assertEquals(o1.getEntry(4, 4).toChar(), Piece.WHITE.toChar());
	  
	  assertFalse(o1.validMove(Piece.BLACK, -1, 0));
	  Othello o2 = new Othello();
	  o2.restart(Piece.BLACK);
	  o2.move(3, 2);
	  assertTrue(o2.gameBoard[3][3].toChar() == Piece.BLACK.toChar());
	  
	  //weird invalid move
	  Othello o3 = new Othello();
	  o3.restart(Piece.BLACK);
	  assertFalse(o3.validMove(Piece.BLACK, 1, 3));
	  o3.move(Piece.BLACK, 1, 3);
	  assertFalse(o3.gameBoard[1][3].toChar() == Piece.BLACK.toChar());
	  
  }
  @Test public void testWinning(){
	  Othello o1 = sample1();
	  o1.restart(Piece.BLACK);
	  assertEquals(o1.winning().toChar(), Piece.NONE.toChar());
	  
	  o1.setTurn(Piece.WHITE);
	  o1.move(Piece.WHITE, 3, 5);
	  assertEquals(o1.winning().toChar(), Piece.WHITE.toChar());
	  assertFalse(o1.winning().toChar() == Piece.BLACK.toChar());
  }
  
  private Othello sample1(){
	  Othello o1 = new Othello();
	  o1.setEntry(Piece.BLACK, 1, 1);
	  o1.setTurn(Piece.WHITE);
	  return o1;
  }

}
