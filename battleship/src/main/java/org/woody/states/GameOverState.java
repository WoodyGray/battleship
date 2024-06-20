package org.woody.states;

import org.woody.server.BattleshipGameServerEndpoint;
import org.woody.stateMachine.EnterState;
import org.woody.stateMachine.StateMachine;
import org.woody.user.HumanPlayerProvider;
import org.woody.user.User;

public class GameOverState implements EnterState {
    public GameOverState(StateMachine stateMachine){
        this.stateMachine = stateMachine;
    }

    private final StateMachine stateMachine;
    @Override
    public void enter() {
        resetUser();
        BattleshipGameServerEndpoint.resetServer();
        enterGameModeSelectionState();
    }

    private void resetUser(){
        User user = HumanPlayerProvider.getInstance();
        user.getLeftBoard().resetMap();
        user.getRightBoard().resetMap();
    }

    private void enterGameModeSelectionState(){
        this.stateMachine.changeState(GameModeSelectionState.class);
    }
}
