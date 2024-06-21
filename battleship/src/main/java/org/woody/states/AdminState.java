package org.woody.states;

import org.woody.server.LoggingClientEndpoint;
import org.woody.stateMachine.EnterState;
import org.woody.stateMachine.StateMachine;
import org.woody.util.ConsoleDisplayManager;
import org.woody.util.UserInteractionManager;

import java.io.IOException;


public class AdminState implements EnterState {
    private static final String HELP_COMMAND = "--help";
    private static final String HISTORY_COMMAND = "--history";

    private static final String EXIT_COMMAND = "--exit";

    public AdminState(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    private final StateMachine stateMachine;
    @Override
    public void enter() {
        ConsoleDisplayManager.printAdminMode();
        chooseAdminCommand();
    }

    private void chooseAdminCommand(){
        String command;
        do {
            command = UserInteractionManager.getAdminCommandFromInput();
            if (HELP_COMMAND.equals(command)) {
                printAllCommands();
            } else if(HISTORY_COMMAND.equals(command)){
                getGamesFromLoggingServer();
            } else if(EXIT_COMMAND.equals(command)){
                enterPrepareGameState();
            }
        }while (!EXIT_COMMAND.equals(command));
    }

    private void printAllCommands(){
        System.out.println("Список доступных команд:");
        System.out.println(HELP_COMMAND + " - выводит все доступные команды");
        System.out.println(HISTORY_COMMAND + " - выводит историю всех партий в формате json");
        System.out.println(EXIT_COMMAND + " - позволяет вернуться в режим игрока");
    }

    private void getGamesFromLoggingServer(){
        LoggingClientEndpoint.startClient();
        try {
            LoggingClientEndpoint.sendHistoryCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void enterPrepareGameState() {this.stateMachine.changeState(PrepareGameState.class);}
}
