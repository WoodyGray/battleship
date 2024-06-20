package org.woody.util;

import org.woody.board.ship.ShipType;
import org.woody.user.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ConsoleDisplayManager {
    public enum AnsiColor {
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        RESET("\u001B[0m");

        private final String code;

        AnsiColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static void printHeader() {
        clearConsole();
        System.out.println(createGameHeader());
    }

    public static void printGameModeMenu() {
        String message = """
                Выбери режим игры:
                A. Одиночный (пока в работе)
                B. По сети (один на один с другим играком)
                            
                Выбирай (A или B):\s""";
        System.out.print(message);
    }

    public static void printOrientationMenu() {
        String message = "Выбери ориентацию кораблся (H для горизонтали, V для вертикали): ";
        System.out.print(message);
    }
    public static void printEmptyRows(int numRows) {
        for(int i = 0; i < numRows; i++) {
            System.out.println();
        }
    }

    public static void printShipPlacementModeMenu() {
        String message = """
                Выбери способ растановки кораблей:
                A. Автоматически (компьюотер сам выберет)
                B. Вручную 
                            
                Выбирай (A или B):\s""";
        System.out.print(message);
    }

    public static void printMultiplayerMenu() {
        String message = """
                Теперь нужно выбрать роль в игре:
                A. Хост (Создать игру)
                B. Клиент (Присоедениться)
                                           
                Выбирай (A или B):\s""";
        System.out.print(message);
    }

    public static void printPositionInputMessage() {
        System.out.print("\nВведи позицию корабля (Диапазон A-P для букв and 1-16 для чисел, пример, A13): ");
    }

    public static void welcomeUser(String userName) {
        System.out.printf("Привет, %s%s%s! Ты присоеденился к игре морской бой!\n\n", AnsiColor.GREEN, userName, AnsiColor.RESET);
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("bash", "-c", "clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printPlacementMessage(ShipType shipType, int totalShipsPlacedOfThisType, int totalShipsPlaced) {
        String shipName = ShipType.convertShipTypeToNormalString(shipType);
        int shipSize = shipType.getShipLength();
        int totalShipsOfThisType = shipType.getNumShips();
        int totalShips = ShipType.sizeAllShips();
        String message = String.format("Ты поставил корабль %s размера %d. После этого, у тебя осталось %d такого типа, чтобы поставить,\nи %d общее кол-воб, для любых кораблей.\n",
                shipName, shipSize, totalShipsOfThisType - totalShipsPlacedOfThisType - 1, totalShips - totalShipsPlaced - 1);
        System.out.println(message);
    }

    private static String createGameHeader() {

        String text = """
                 _   _   _   _   _   _   _     _   _   _ \s
                 / \\ / \\ / \\ / \\ / \\ / \\ / \\   / \\ / \\ / \\\s
                ( M | O | R | S | K | O | I ) ( B | O | I )
                 \\_/ \\_/ \\_/ \\_/ \\_/ \\_/ \\_/   \\_/ \\_/ \\_/\s 
                """;

        int totalLength = 109;
        int padding = (totalLength - 69) / 2;
        String paddingSpaces = " ".repeat(padding);

        return Arrays.stream(text.split("\\n"))
                .map(line -> paddingSpaces + line + paddingSpaces)
                .collect(Collectors.joining("\n")) + "\n";
    }

    private static String createBoardNamesHeader(String userName, String opponentName) {
        String firstPlayerName = String.format("   %s", userName);
        String secondPlayerName = String.format("   %s", opponentName);
        return String.format("\n%-44s   %s%n", firstPlayerName, secondPlayerName);
    }

    private static String createRemainingShipsHeader(int userCount, int opponentCount) {
        String firstPlayerCount = String.format("   Оставшиеся корабли: %d", userCount );
        String secondPlayerCount = String.format("   Оставшиеся корабли: %d", opponentCount);
        return String.format("%-44s   %s%n", firstPlayerCount, secondPlayerCount);
    }

    private static String createBattleBody(String[]... args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args[0].length; i++) {
            if (args.length > 2 && i < args[2].length) {
                sb.append(String.format("%-35s   %-35s   %s%n", args[0][i], args[1][i], args[2][i]));
            } else if (i == 12) {
                sb.append(String.format("%-35s   %-35s   %s%n", args[0][i], args[1][i], getTurnMessage(args[3][0])));
            } else {
                sb.append(String.format("%-35s   %s%n", args[0][i], args[1][i]));
            }
        }
        return sb.toString();
    }

    public static String getGameInfo(User user, String opponentName, int totalTurns) {
        StringBuilder sb = new StringBuilder();
        String gameHeader = createGameHeader();
        sb.append(gameHeader);

        String boardNamesHeader = createBoardNamesHeader(user.getName(), opponentName);
        sb.append(boardNamesHeader);

        int userCount = user.getLeftBoard().getRemainingShipsCount();
        int opponentCount = user.getRightBoard().getRemainingShipsSum();
        String remainingShipsHeader = createRemainingShipsHeader(userCount, opponentCount);
        sb.append(remainingShipsHeader);

        String[] board1 = user.getLeftBoard().getState().split("\n");
        String[] board2 = user.getRightBoard().getState().split("\n");
        String[] remainingShips = user.getRightBoard().getShipsState().split("\n");

        String boardsAndShips = createBattleBody(board1, board2, remainingShips, new String[]{String.valueOf(totalTurns)});
        sb.append(boardsAndShips);

        return sb.toString();
    }

    public static void printGameSetup(User user) {
        printHeader();
        welcomeUser(user.getName());
        printGameModeMenu();
        UserInteractionManager.setABSelectionInterpreter();
    }

    public static void printMultiplayerSetup() {
        printHeader();
        printMultiplayerMenu();
        UserInteractionManager.setABSelectionInterpreter();
    }

    public static void printShipPlacementSetup() {
        clearConsole();
        printHeader();
        printShipPlacementModeMenu();
        UserInteractionManager.setABSelectionInterpreter();
    }

    public static String getSunkMessage() {
        return "ПОТОП";
    }

    public static String getMissMessage() {
        return "ПРОМОХНУЛСЯ";
    }

    public static String getHitMessage() {
        return "ПОПАЛ";
    }

    public static String getPlayerTurnMessage() {
        return "твой ход";
    }

    public static String getPositionMessage(String position) {
        return position;
    }

    public static String getOpponentTurnMessage(String opponentName) {
        return String.format("%s ходит", opponentName.toUpperCase());
    }

    public static String getOpponentAttackMessage(String opponentName, String message) {
        return String.format("%s attacked %s", opponentName, getPositionMessage(message));
    }

    public static String getPlayerShipStatus(String shipName, int shipSize) {
        return String.format("Твой %s размера %d был %s-лен", shipName, shipSize, getSunkMessage());
    }

    public static String getPlayerImminentAttackWarning(String opponentName) {
        return String.format("%s %s! Он атакует снова", opponentName, getHitMessage());
    }

    public static String getOpponentMissMessage(String opponentName) {
        return String.format("%s %s", opponentName, getMissMessage());
    }

    public static String getPlayerShipSunkMessage(String shipName, int shipSize) {
        return String.format("У тебя %s-ли %s размера %d", getSunkMessage(), shipName, shipSize);
    }

    public static String getPlayerMissMessage() {
        return String.format("Ты %s", getMissMessage());
    }

    public static String getPlayerHitMessage() {
        return String.format("Ты %s! Можешь атаковать снова", getHitMessage());
    }

    public static String getTurnMessage(String totalTurns) {
        String s = String.format("Ход: %s", totalTurns);
        s = String.format("%31s", s);
        return String.format("%-31s", s);
    }

    public static void printMatchResult(boolean flag, String name) {
        AnsiColor color = flag ? AnsiColor.RED : AnsiColor.GREEN;
        String winnerName = flag ? name : "Ты";
        System.out.printf("%sКонец игры. %s победитель:%s\n\n", color, winnerName, AnsiColor.RESET);
    }

    public static String getPlayerMatchStatus(boolean flag) {
        AnsiColor color = flag ? AnsiColor.RED : AnsiColor.GREEN;
        String status = flag ? "ПОРАЖЕНИЕ" : "ПОБЕДА";
        return String.format("%s%s%s", color, status, AnsiColor.RESET);
    }

    public static void getMatchTimes(String matchStartTime, String matchEndTime, Duration duration) {
        String startTime = String.format("Начало матча: %s\n", matchStartTime);
        String endTime = String.format("Конец матча: %s\n", matchEndTime);
        String elapsedTime = String.format("Время всей игры: %d часы, %d минуты\n", duration.toHours(), duration.toMinutes());
        System.out.println(startTime + endTime + elapsedTime);
    }

    public static void printSessionClosure(String id, String message) {
        System.out.printf("Сессия %s закрыта, так как %s\n", id, message);
    }

    public static String getReasonMessage() {
        return "игра закончена";
    }

    public static String getWatchOnlyMessage() {
        return String.format("\n%sТы можешь только смотреть%s\n", AnsiColor.YELLOW, AnsiColor.RESET);
    }

    public static void printStatsMessage() {
        System.out.printf("%sСтатистика%s\n\n", AnsiColor.YELLOW, AnsiColor.RESET);
    }

    public static void printEfficiency(long efficiency) {
        System.out.printf("Эффективность: %d%%\n\n", efficiency);
    }

    public static void printPlayerStats(String playerName, boolean hasLost, String remainingShips) {
        String ships = hasLost ? "не осталось ни одного корабля" : remainingShips;
        System.out.printf("Игрок: %s\nСтатус: %s\nОставшиеся корабли: %s\n", playerName, getPlayerMatchStatus(hasLost), ships);
    }
}
