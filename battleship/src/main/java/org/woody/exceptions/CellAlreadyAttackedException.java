package org.woody.exceptions;

import org.woody.util.ConsoleDisplayManager;

public class CellAlreadyAttackedException extends Exception {
    public CellAlreadyAttackedException(String message) {
        super(ConsoleDisplayManager.AnsiColor.RED + message + ConsoleDisplayManager.AnsiColor.RESET);
    }
}
