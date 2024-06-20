package org.woody.user;

import org.woody.board.cell.Cell;

public interface Player {
    String attackOpponent();
    Cell giveResponse(Cell position);
    boolean hasLost();
}
