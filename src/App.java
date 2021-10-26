import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.SourceDataLine;

import java.io.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    static List<Character> userGuesses = new ArrayList();
    ArrayList<String> words = new ArrayList<>();
    ArrayList<User> users = new ArrayList<User>();
    Scanner read = new Scanner(System.in);
    boolean playing = true;
    String desktopPath = System.getProperty("user.home") + "/Desktop";

    public static void main(String[] args) throws Exception {
        App main = new App();
        main.printMenu();

    }

    public void cleanLists() {
        userGuesses.clear();
    }

    // Creating User Object in order to have him in Leader board
    public void createUser() {
        System.out.println("Please Enter your User Name");
        String userName = read.nextLine();
        System.out.println("Please Enter your Age");
        int userAge = read.nextInt();
        User newUser = new User(userName, userAge);
        users.add(newUser);
    }

    public void startGame() {
        createUser(); // Creating User Object

        int successWords = 0;
        int unsuccessfulWords = 0;

        String word = randomWord(); // Getting Random Word From List
        // System.out.println("WORD: " + word);
        while (true && playing) {
            printWord(word);
            System.out.println(); // Printing Next Line
            String userChar = read.nextLine().toLowerCase(); // Reading Player Input

            // Validation for empty words
            while (userChar.isEmpty()) {
                System.out.println("Please Enter Character!");
                userChar = read.nextLine().toLowerCase();
            }

            userGuesses.add(userChar.charAt(0)); // Adding users entered character to userguess list<characters>

            // Checking if character exist in word
            if (isCharacterInWord(word.toLowerCase(), userChar.toLowerCase())
                    && !numberCharacter(userChar.toLowerCase())) {
                if (numberCharactersInWord(word, userChar.toLowerCase()) > 1) {
                    successWords += numberCharactersInWord(word, userChar.toLowerCase());
                } else {
                    successWords++;
                }

            } else if (!isCharacterInWord(word, userChar)) {
                unsuccessfulWords++;
                printImage(unsuccessfulWords);
            } else {
                System.out.println("You have entered already this characters , Please choose another!");
            }

            if (word.length() == successWords) {
                System.out.println("Congratulations " + users.get(0).getName() + "You Won!");
                System.out.println("Successfull Words:" + successWords + " Unsuccessfull words:" + unsuccessfulWords);
                System.out.println("Would You Like To Play Again? If Yes Enter  yes or no ");
                if (read.nextLine().equals("yes")) {
                    printMenu();
                } else {
                    endGame();
                }

            }

        }

    }

    public void endGame() {

        for (int i = 0; i < 7; i++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.print(e);
            }
            System.out.print(".");
        }
        playing = false;
        System.out.println("");
        printPlayer();
        System.out.println("Thank you for playing!");
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
            System.out.println("You lost " + users.get(users.size() - 1).getName() + ", Try Again!");
            System.out.println("Would You Like To Play Again? If Yes Enter  yes or no ");
            if (read.nextLine().equals("yes")) {
                printMenu();
            } else {
                endGame();
            }

        }
    }

    public void printPlayer() {
        System.out.println("Players of The Game!");
        for (User player : users) {
            System.out.print(player.getName() + ":" + player.getAge());
            System.out.println();
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

    // Returning number of user entered character in the word
    public int numberCharactersInWord(String word, String target) {
        int times = 0;
        char temp;

        for (int i = 0; i < word.length(); i++) {
            temp = word.charAt(i);
            if (target.equals(temp + "")) {
                times++;
            }

        }
        return times;
    }

    // Checking if user input character is in the word
    public boolean isCharacterInWord(String word, String target) {

        if (word.contains(target)) {
            return true;
        } else {
            return false;
        }

    }

    // Creating Random Word From The List
    public String randomWord() {
        Random rand = new Random();
        return words.get(rand.nextInt(words.size())).toLowerCase();

    }

    // User Menu Interface
    public void printMenu() {
        fetchData(); // Fetching Words From TXT.
        cleanLists();
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

    // Method in order to create text file
    public void createTextFile() throws IOException {
        File fl = new File("D:/OneDrive/Desktop/words.txt");
        // File fl = new File(desktopPath);
        fl.createNewFile();
        System.out.println("File Created path = " + fl.getAbsolutePath());

    }

    // Fetching Data From .txt File
    public void fetchData() {
        try {
            Scanner sc = new Scanner(new File("D:/OneDrive/Desktop/words.txt"));
            while (sc.hasNextLine()) {
                // System.out.println(sc.nextLine());
                words.add(sc.nextLine());

            }

        } catch (FileNotFoundException e) {
            try {
                createTextFile();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

        }

    }

}
