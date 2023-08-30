package game;

import java.util.*;

class SnakeNLadderStart {

    final static int WINPOINT = 100;

    static Map<Integer, Integer> snake = new HashMap<Integer, Integer>();
    static Map<Integer, Integer> ladder = new HashMap<Integer, Integer>();

    {
        snake.put(99, 54);
        snake.put(70, 55);
        snake.put(52, 42);
        snake.put(25, 2);
        snake.put(95, 72);

        ladder.put(6, 25);
        ladder.put(11, 40);
        ladder.put(60, 85);
        ladder.put(46, 90);
        ladder.put(17, 69);
    }

    public static HashMap<String, Integer> sortByValue(Map<String, Integer> positions) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(positions.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public boolean isWin(int score) {
        return WINPOINT == score;
    }

    public int rollDice() {
        Random rn = new Random();
        return rn.nextInt(6) + 1;
    }

    public int currentPosition(int prevScore, int diceValue) {
        int current = prevScore + diceValue;

        if (current > WINPOINT) {
            current = current - diceValue;
            return current;
        }

        if (snake.containsKey(current)) {
            System.out.println("Oops !! You got Swallowed by the Snake\n");
            current = snake.get(current);
        }

        if (ladder.containsKey(current)) {
            System.out.println("Great!! You climbed up the ladder\n");
            current = ladder.get(current);
        }
        return current;
    }

    public void startGame() {
        Map<String, Integer> positions = new HashMap<String, Integer>();

        Scanner s = new Scanner(System.in);

        System.out.println("Enter total number of players: ");
        int noOfPlayer = s.nextInt();

        if (noOfPlayer <= 1) {
            System.out.println("Please select more than 1 player!!");
            startGame();
        }

        String[] playerName = new String[noOfPlayer];
        int[] playerScore = new int[noOfPlayer];

        System.out.println("Enter Player name(s):");
        for (int i = 0; i < noOfPlayer; i++) {
            playerName[i] = s.next();
            playerScore[i] = 0;
        }

        System.out.println("Game has been started!\n");

        int currentPlayer = 0;
        String str;
        int diceValue = 0, rem = 0;
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println(playerName[currentPlayer] + "'s turn\nPress 'r' to roll the dice");
            str = s.next();
            System.out.println();

            if (str.equalsIgnoreCase("r")) {
                diceValue = rollDice();
                System.out.println("Dice Value: " + diceValue + "\n");

                if (diceValue == 6) {
                    rem += 6;
                    continue;
                } else {
                    playerScore[currentPlayer] = currentPosition(playerScore[currentPlayer], diceValue + rem);

                    System.out.println("Total Score:");

                    for (int i = 0; i < noOfPlayer; i++) {
                        System.out.println(playerName[i] + " Score: " + playerScore[i]);
                    }

                    System.out.println();

                    if (isWin(playerScore[currentPlayer])) {
                        int size = positions.size() + 1;
                        positions.put(playerName[currentPlayer], size);
                        System.out.println("Congratulations " + playerName[currentPlayer] + ", your position is " + size);

                        if (size == noOfPlayer - 1) {
                            currentPlayer++;
                            currentPlayer = currentPlayer % noOfPlayer;

                            while (positions.containsKey(playerName[currentPlayer])) {
                                currentPlayer++;
                                currentPlayer = currentPlayer % noOfPlayer;
                            }

                            System.out.println(playerName[currentPlayer] + " loses");

                            size = positions.size() + 1;
                            positions.put(playerName[currentPlayer], size);

                            Map<String, Integer> sortedPositions = sortByValue(positions);

                            System.out.println("All positions are:");

                            for (String name : sortedPositions.keySet()) {
                                String key = name.toString();
                                String value = sortedPositions.get(name).toString();
                                System.out.println(key + " " + value);
                            }

                            gameOver = true;
                            break;
                        }
                    }

                    currentPlayer++;
                    currentPlayer = currentPlayer % noOfPlayer;

                    while (positions.containsKey(playerName[currentPlayer])) {
                        currentPlayer++;
                        currentPlayer = currentPlayer % noOfPlayer;
                    }
                    rem = 0;

                }

            } else {
                System.out.println("Please enter the correct value!!");
                continue;
            }
        }
    }
}

public class SnakeNLadder {

    public static void printIntro() {
        System.out.println("Welcome To Snakes And Ladders Game!\n");
        System.out.println("This program runs like a regular snakes and ladders game,");
        System.out.println("where you will be playing against other players.\n");
        System.out.println("Instructions:\n");
        System.out.println("1. Each player starts the game at square 1");
        System.out.println("2. The first player to reach square 100 wins the game");
        System.out.println("3. There are preset squares representing snakes and ladders");
        System.out.println("   - If you land on a snake, you move down a few squares");
        System.out.println("   - If you land on a ladder, you move up a few squares\n");
        System.out.println("Snake positions: 99-54, 70-55, 52-42, 25-2, 95-72");
        System.out.println("Ladder positions: 6-25, 11-40, 17-69, 60-85, 46-90");
        System.out.println("\nLet's start the game!\nGood luck and have fun!\n");
    }

    public static void main(String[] args) {

        printIntro();

        SnakeNLadderStart s = new SnakeNLadderStart();
        s.startGame();
    }
}

