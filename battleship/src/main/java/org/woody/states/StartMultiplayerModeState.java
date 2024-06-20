package org.woody.states;

import org.woody.board.ship.ShipPlacementManager;
import org.woody.client.BattleshipGameClientEndpoint;
import org.woody.game.BattleController;
import org.woody.server.BattleshipGameServerEndpoint;
import org.woody.stateMachine.*;
import org.woody.user.HumanPlayerProvider;
import org.woody.user.User;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;

public class StartMultiplayerModeState implements EnterState {
    public StartMultiplayerModeState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    private static final String A_MODE = "A";

    private final StateMachine stateMachine;

    @Override
    public void enter() {
        startMultiplayerGame();
        enterGameOverState();
    }

    private void startMultiplayerGame() {
        User user = HumanPlayerProvider.getInstance();

        ShipPlacementManager manager = new ShipPlacementManager();
        manager.setupShips(user.getLeftBoard());

        ConsoleDisplayManager.printMultiplayerSetup();
        String selectedMode = UserInteractionManager.getABSelectionFromInput();

        UserInteractionManager.setPortInterpreter();
        System.out.print("Выбери свой порт в диапазоне от 1500 до 8000: ");
        int port = UserInteractionManager.getPortFromInput();
        ConsoleDisplayManager.clearConsole();

        BattleController playerBattleController = new BattleController(user);

        if (A_MODE.equals(selectedMode)) {
            BattleshipGameServerEndpoint.startServer(port, playerBattleController);
        } else {
            BattleshipGameClientEndpoint.startClient(port, playerBattleController);
        }
        BattleController.waitForUserInput();
        if (playerBattleController.isMatchFinished()) {
            playerBattleController.printMatchStats();
        }
    }

    private void enterGameOverState() {
        this.stateMachine.changeState(GameOverState.class);
    }
}
