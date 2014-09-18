/*
 * Name:	Alexandria BenDebba 
 * UID: 	112366018 
 * Section: 0303
 */

package othello;

public enum Piece {

  BLACK, WHITE, NONE;

  public char toChar() {
    switch (this) {
      case BLACK:
        return 'b';
      case WHITE:
        return 'w';
      case NONE:
        return '-';
    }

    return '-';  // make compiler happy even though we should never reach here
  }

}
