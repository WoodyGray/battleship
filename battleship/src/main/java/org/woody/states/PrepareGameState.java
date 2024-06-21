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
        String userName = getUserName();
        if (userName.equals("admin")){
            enterAdminState();
        }else {
            createUser();
            setupUser(userName);
            enterGameModeSelectionState();
        }
    }

    private String getUserName(){
        return UserInteractionManager.getInputNameFromUser();
    }

    private void createUser() {
        HumanPlayerProvider.getInstance();
    }

    private void setupUser(String userName) {
        User user = HumanPlayerProvider.getInstance();

        CompleteBoard leftBoard = new CompleteBoard();
        BasicBoard rightBoard = new BasicBoard();

        user.setName(userName);
        user.setLeftBoard(leftBoard);
        user.setRightBoard(rightBoard);
    }

    private void enterAdminState(){
        this.stateMachine.changeState(AdminState.class);
    }

    private void enterGameModeSelectionState() {
        this.stateMachine.changeState(GameModeSelectionState.class);
    }
}