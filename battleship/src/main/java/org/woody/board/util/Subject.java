package org.woody.board.util;

public interface Subject {
    void attach(Observer observer);
    void notifyObserver();
}

