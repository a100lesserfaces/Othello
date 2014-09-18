package tests;

/* This class compares a two-dimensional character array to an Othello
 * object's board, to faciliate writing tests.  A character array of the
 * expected results of a game can be created more easily than having to
 * check every position of the board separately.
 */

import othello.Othello;

public class TestCode {

  private static final int SIZE= 8;

  // compares the elements of its parameter othello to the elements of the
  // array expected, returning true if the array is a representation of the
  // same contents as the othello board's position
  public static boolean compareToArray(Othello othello, char[][] expected) {
    int i, j;
    boolean answer= true;

    i= 0;
    // continue loop until all rows are processed, or until the array and
    // the othello board's contents have been found to differ in any row
    while (i < SIZE && answer) {
      j= 0;
      // continue loop until either reaching the end of the row, or
      // until finding a position where the array and the othello board
      // disagree
      while (j < SIZE && expected[i][j] == othello.getEntry(i, j).toChar())
        j++;
      // if the loop stopped because a position was found where the
      // array and the othello board disagreed, before getting to the
      // end of the row, the method returns false
      if (j < SIZE)
        answer= false;

      i++;
    }  // while

    return answer;
  }

}
