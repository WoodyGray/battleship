package org.woody.exceptions;

import org.woody.util.ConsoleDisplayManager;

public class InvalidShipPlacementException extends Exception {
    public InvalidShipPlacementException(String message) {
        super(ConsoleDisplayManager.AnsiColor.RED + message + ConsoleDisplayManager.AnsiColor.RESET);
    }
}
