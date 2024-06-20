package org.woody.user;

import org.woody.board.BasicBoard;
import org.woody.board.CompleteBoard;
import org.woody.board.cell.Cell;
import lombok.Data;

@Data
public abstract class User implements Player {
    private String name;
    private CompleteBoard leftBoard;
    private BasicBoard rightBoard;

    public abstract void updateRightBoard(Cell cell);
}
