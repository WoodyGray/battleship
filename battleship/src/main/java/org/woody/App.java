package org.woody;

import org.woody.stateMachine.StateMachine;
import org.woody.states.*;

/**
 * Hello world!
 *
 */
public class App {
    private static StateMachine stateMachine;

    public static void main(String[] args) {
        createAndSetupStateMachine();
        enterPrepareGameState();
    }


    private static void createAndSetupStateMachine() {
        stateMachine = new StateMachine();

        PrepareGameState prepareGameState = new PrepareGameState(stateMachine);
        GameModeSelectionState gameModeSelectionState = new GameModeSelectionState(stateMachine);
        AdminState adminState = new AdminState(stateMachine);
        StartMultiplayerModeState startMultiplayerModeState = new StartMultiplayerModeState(stateMachine);
        GameOverState gameOverState = new GameOverState(stateMachine);

        stateMachine.addState(prepareGameState);
        stateMachine.addState(adminState);
        stateMachine.addState(gameModeSelectionState);
        stateMachine.addState(startMultiplayerModeState);
        stateMachine.addState(gameOverState);
    }

    private static void enterPrepareGameState() {
        stateMachine.changeState(PrepareGameState.class);
    }
}
