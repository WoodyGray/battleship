package org.woody;

import org.woody.stateMachine.StateMachine;
import org.woody.states.GameModeSelectionState;
import org.woody.states.GameOverState;
import org.woody.states.PrepareGameState;
import org.woody.states.StartMultiplayerModeState;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
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
        StartMultiplayerModeState startMultiplayerModeState = new StartMultiplayerModeState(stateMachine);
        GameOverState gameOverState = new GameOverState(stateMachine);

        stateMachine.addState(prepareGameState);
        stateMachine.addState(gameModeSelectionState);
        stateMachine.addState(startMultiplayerModeState);
        stateMachine.addState(gameOverState);
    }

    private static void enterPrepareGameState() {
        stateMachine.changeState(PrepareGameState.class);
    }
}
