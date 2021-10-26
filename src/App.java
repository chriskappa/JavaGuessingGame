import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    ArrayList<String> words = new ArrayList<>();
    static List<Character> userGuesses = new ArrayList();
    Scanner read = new Scanner(System.in);
    boolean playing = true;

    public static void main(String[] args) throws Exception {
        App main = new App();

        main.printMenu();

    }

    public void startGame() {
        int successWords = 0;
        int unsuccessfulWords = 0;

        String word = randomWord();
        System.out.println("WORD: " + word);
        while (true && playing) {
            System.out.println(); // Printing Next Line
            String userChar = read.nextLine(); // Reading Player Input

            // Validation for empty words
            while (userChar.isEmpty()) {
                userChar = read.nextLine();
            }

            userGuesses.add(userChar.charAt(0));

            if (isCharacterInWord(word, userChar) && !numberCharacter(userChar)) {
                successWords++;

            } else if (!isCharacterInWord(word, userChar)) {
                unsuccessfulWords++;
                printImage(unsuccessfulWords);
            } else {
                System.out.println("You have entered already this characters , Please choose another!");
            }

            if (word.length() == successWords) {
                System.out.println("Congratulations You Won!");
                System.out.println("Successfull Words:" + successWords + " Unsuccessfull words:" + unsuccessfulWords);
                break;
            }

            printWord(word);

        }

    }

    public void printImage(int num) {
        System.out.println("________Playing________");

        if (num == 1) {
            System.out.println("O");
        } else if (num == 2) {
            System.out.println("O");
            System.out.println("|");
        } else if (num == 3) {
            System.out.println("O");
            System.out.println("|");
            System.out.println("|");
        } else if (num == 4) {
            System.out.println(" O");
            System.out.println(" |");
            System.out.println(" |");
            System.out.println("/");
        } else if (num == 5) {
            System.out.println(" O");
            System.out.println(" |");
            System.out.println(" |");
            System.out.println("/\\");
        }

        System.out.println("_______________________");
        if (num == 5) {
            System.out.println("You lost, Try Again!");
            System.out.println("Would You like to play again? if yes enter  yes or no ");
            if (read.nextLine().equals("yes")) {
                printMenu();
            } else {

                for (int i = 0; i < 7; i++) {
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                        System.out.print(e);
                    }
                    System.out.print(".");
                }
                playing = false;
                System.out.println("");
                System.out.println("Thank you for playing!");
            }

        }
    }

    public void printWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (userGuesses.contains(word.charAt(i))) {

                System.out.print(word.charAt(i));

            } else {
                System.out.print("-");
            }
        }
    }

    public boolean numberCharacter(String oneCharacter) {
        int times = 0;
        for (Character word : userGuesses) {
            if (word == oneCharacter.charAt(0)) {
                times++;
            }
        }
        if (times > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCharacterInWord(String word, String target) {

        if (word.contains(target)) {
            return true;
        } else {
            return false;
        }

    }

    public String randomWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));

    }

    // User Menu Interface
    public void printMenu() {
        fetchData(); // Fetching Words From TXT.

        while (true) {
            System.out.println("Welcome To The Game");
            System.out.println("1)Start Game");
            System.out.println("2)Random Function");
            System.out.println("Exit");
            String choice = read.nextLine().toLowerCase();
            if (choice.equals("1")) {
                startGame();
                break;
            } else if (choice.equals("exit")) {
                System.out.println("Your choice is not valid!");

            }

        }

    }

    // Fetching Data From TXT File
    public void fetchData() {
        try {
            Scanner sc = new Scanner(new File("D:/OneDrive/Desktop/words.txt"));
            while (sc.hasNextLine()) {
                // System.out.println(sc.nextLine());
                words.add(sc.nextLine());

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
