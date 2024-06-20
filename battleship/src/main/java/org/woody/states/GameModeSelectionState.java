package org.woody.states;

import org.woody.stateMachine.*;
import org.woody.user.HumanPlayerProvider;
import org.woody.user.User;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;

public class GameModeSelectionState implements EnterState {
    public GameModeSelectionState(StateMachine stateMachine){
        this.stateMachine = stateMachine;
    }

    private static final String A_MODE = "A";

    private final StateMachine stateMachine;
    @Override
    public void enter(){
        printGameSetup();
        chooseGameMode();
    }

    private void printGameSetup(){
        User user = HumanPlayerProvider.getInstance();
        ConsoleDisplayManager.printGameSetup(user);
    }

    private void chooseGameMode() {
        String selectedMode = UserInteractionManager.getABSelectionFromInput();

        if (A_MODE.equals(selectedMode)) {
//            тут должен быть одиночный режим
            //todo
        } else {
            enterStartMultiplayerModeState();
        }
    }

    private void enterStartMultiplayerModeState(){
        this.stateMachine.changeState(StartMultiplayerModeState.class);
    }
}
