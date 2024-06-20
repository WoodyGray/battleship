package org.woody.states;

import org.woody.board.BasicBoard;
import org.woody.board.CompleteBoard;
import org.woody.stateMachine.*;
import org.woody.user.HumanPlayerProvider;
import org.woody.user.User;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;

public class PrepareGameState implements EnterState {
    public PrepareGameState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    private final StateMachine stateMachine;

    @Override
    public void enter() {
        ConsoleDisplayManager.printHeader();
        createUser();
        setupUser();
        enterGameModeSelectionState();
    }

    private void createUser() {
        HumanPlayerProvider.getInstance();
    }

    private void setupUser() {
        User user = HumanPlayerProvider.getInstance();

        String userName = UserInteractionManager.getInputNameFromUser();
        CompleteBoard leftBoard = new CompleteBoard();
        BasicBoard rightBoard = new BasicBoard();

        user.setName(userName);
        user.setLeftBoard(leftBoard);
        user.setRightBoard(rightBoard);
    }

    private void enterGameModeSelectionState() {
        this.stateMachine.changeState(GameModeSelectionState.class);
    }
}