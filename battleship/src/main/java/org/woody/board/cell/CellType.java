package org.woody.board.cell;

import org.woody.util.ConsoleDisplayManager;
import lombok.Getter;

@Getter
public enum CellType {
    WATER( "."),
    MISS("×"),
    SHIP( "☐"),
    HIT("☒"),
    SUNK("☒");


    private final String symbol;

    CellType(String symbol) {
        this.symbol = symbol;
    }

    public static boolean isCellState(String message) {
        try {
            CellType.valueOf(message);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}