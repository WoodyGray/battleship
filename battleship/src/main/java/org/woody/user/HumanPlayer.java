package org.woody.user;

import org.woody.board.cell.Cell;
import org.woody.exceptions.CellAlreadyAttackedException;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;

public class HumanPlayer extends User {

    @Override
    public String attackOpponent() {
        String target;
        while (true) {
            try {
                UserInteractionManager.setPositionInterpreter();
                ConsoleDisplayManager.printPositionInputMessage();
                target = UserInteractionManager.createPositionFromInput();
                if (getRightBoard().hasAttacked(target)) {
                    throw new CellAlreadyAttackedException("Ты уже выбирал эту точку");
                }
                break;
            } catch (CellAlreadyAttackedException e) {
                System.out.println(e.getMessage());
            }
        }
        return target;
    }

    @Override
    public Cell giveResponse(Cell position) {
        return super.getLeftBoard().updatePosition(position);
    }

    @Override
    public void updateRightBoard(Cell cell) {
        super.getRightBoard().updatePosition(cell);
    }

    @Override
    public boolean hasLost() {
        return super.getLeftBoard().hasShipsOnBoard();
    }
}
