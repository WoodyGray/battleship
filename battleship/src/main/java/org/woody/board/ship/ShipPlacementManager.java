package org.woody.board.ship;

import org.woody.board.Board;
import org.woody.board.CompleteBoard;
import org.woody.board.cell.Cell;
import org.woody.board.cell.CellType;
import org.woody.exceptions.InvalidShipPlacementException;
import org.woody.util.CellConverter;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShipPlacementManager {
    private final Random random = new Random();
    @Setter
    private CompleteBoard board;

    public void setupShips(CompleteBoard board) {
        ConsoleDisplayManager.printShipPlacementSetup();
        this.setBoard(board);

        String selectedMode = UserInteractionManager.getABSelectionFromInput();
        this.placeShipsOnBoard(selectedMode);
    }

    public void placeShipsOnBoard(String selectedMode) {
        if ("A".equals(selectedMode)) {
            placeShipsAutomatically();
        } else {
            placeShipsManually();
        }
    }

    public void placeShipsAutomatically() {
        boolean allShipsPlaced;
        do {
            this.board.resetMap();
            allShipsPlaced = true;
            for (ShipType shipType : ShipType.values()) {
                for (int i = 0; i < shipType.getNumShips(); i++) {
                    if (!placeShipIfPossible(shipType)) {
                        allShipsPlaced = false;
                        break;
                    }
                }
                if (!allShipsPlaced) {
                    break;
                }
            }
        } while (!allShipsPlaced);
    }

    private void placeShipsManually() {
        for (ShipType shipType : ShipType.values()) {
            for (int i = 0; i < shipType.getNumShips(); i++) {
                ConsoleDisplayManager.printHeader();
                int totalShipsPlacedOfThisType = this.board.getTotalShipsPlacedOfThisType(shipType);
                int totalShipsPlaced = this.board.getShips().size();
                ConsoleDisplayManager.printPlacementMessage(shipType, totalShipsPlacedOfThisType, totalShipsPlaced);
                System.out.println(this.board.getState());
                placeShipManually(shipType);
            }
        }
    }

    private void placeShipManually(ShipType shipType) {
        while (true) {
            try {
                Cell position = getPositionFromUser();
                boolean isVertical = getOrientationFromUser();
                placeShip(new Ship(shipType), position.getX(), position.getY(), isVertical);
                break;
            } catch (InvalidShipPlacementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private boolean placeShipIfPossible(ShipType shipType) {
        List<Integer> positions = getRandomPositions();
        boolean[] orientations = {true, false};

        for (int x : positions) {
            for (int y : positions) {
                for (boolean isVertical : orientations) {
                    try {
                        Ship ship = new Ship(shipType);
                        placeShip(ship, x, y, isVertical);
                        return true;
                    } catch (InvalidShipPlacementException ignored) {
                    }
                }
            }
        }
        return false;
    }

    private void placeShip(Ship ship, int x, int y, boolean isVertical) throws InvalidShipPlacementException {
        for (int i = 0; i < ship.getShipType().getShipLength(); i++) {
            int nx = x + (isVertical ? i : 0);
            int ny = y + (isVertical ? 0 : i);
            if (nx >= Board.BOARD_SIZE || ny >= Board.BOARD_SIZE) {
                throw new InvalidShipPlacementException("\nКорабль выходит за пределы доски.");
            }
            validateCellForPlacement(nx, ny);
            Cell currentCell = this.board.getPosition(nx, ny);
            ship.setPosition(currentCell);
        }
        ship.getPosition().forEach(pos -> pos.setCellType(CellType.SHIP));
        this.board.addShip(ship);
    }

    private Cell getPositionFromUser() {
        UserInteractionManager.setPositionInterpreter();
        ConsoleDisplayManager.printPositionInputMessage();
        String input = UserInteractionManager.createPositionFromInput();
        return CellConverter.createCellFromInput(input);
    }

    private boolean getOrientationFromUser() {
        UserInteractionManager.setOrientationInterpreter();
        ConsoleDisplayManager.printOrientationMenu();
        return UserInteractionManager.getShipOrientationFromInput();
    }

    private List<Integer> getRandomPositions() {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < Board.BOARD_SIZE; i++) positions.add(i);
        Collections.shuffle(positions, this.random);
        return positions;
    }

    private void validateCellForPlacement(int x, int y) throws InvalidShipPlacementException {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < Board.BOARD_SIZE && ny >= 0 && ny < Board.BOARD_SIZE && this.board.getPosition(nx, ny).getCellType() != CellType.WATER) {
                    throw new InvalidShipPlacementException("\nНе удается определить местонахождение корабля");
                }
            }
        }
    }
}

