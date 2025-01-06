import java.io.*;
import java.net.*;
import java.util.Scanner;

public class mastermind {
    public static void main(String[] args) {
        Socket  socketclient;
        //to read from the server
        DataInputStream  inputStreamFromTheServer;
        // to write to the server
        DataOutputStream outputStreamForTheServer;
        try {
            socketclient=  new Socket("localhost",42456);
            inputStreamFromTheServer = new DataInputStream(socketclient.getInputStream());
            outputStreamForTheServer = new DataOutputStream(socketclient.getOutputStream());
            int numberOfGuessesCounter = 0;
            // initialize it with an empty string, this keeps the track of what the user guess is.
            String userGuessString="";
            // we can only guess upto 20 times, So run the loop till 20 times
            while(numberOfGuessesCounter <= 20 && (!(userGuessString.equalsIgnoreCase("QUIT")))) {
                String receivedMessageFromTheServer = inputStreamFromTheServer.readUTF();
                // print out the user entered string and the response sent by the server to the client.
                System.out.println(userGuessString+"\t" + receivedMessageFromTheServer);
                if(receivedMessageFromTheServer.equalsIgnoreCase("PPPP")) {
                    System.out.println("Congratulations!!! You guessed the code correctly in "+ numberOfGuessesCounter +" moves");
                    //exit the program
                    return;
                }
                if(receivedMessageFromTheServer.equalsIgnoreCase("GO")) {
                    System.out.println("Welcome to Mastermind. You will try to guess a 4 color code using \n" +
                            "only the letters ( R, Y, G, B, W, O). You will lose the game if you \n" +
                            "are unable to guess the code correctly in 20 guesses.  Good Luck!");
                }
                // this reads the user input from the standard input until it is not verified;
                do{
                    System.out.println("Please enter your guess for the secret code or “QUIT” ");
                    Scanner input = new Scanner(System.in);
                    userGuessString = input.nextLine();
                    if(userGuessString.equalsIgnoreCase("QUIT")) {
                        System.out.println("Goodbye but please play again!");
                        // send the message to the server. The message would be the userGuessString. Since we are sending "QUIT", we can just exit the program; Could have sent the string litereal "QUIT" as well.
                        outputStreamForTheServer.writeUTF(userGuessString);
                        return;
                    }
                } while(!verifyInput(userGuessString));
                // send the user's guess to the server
                outputStreamForTheServer.writeUTF(userGuessString);
                numberOfGuessesCounter++;
            }

        }
        catch(IOException e) {
            System.out.println("Something went wrong!!!");
            e.printStackTrace();
        }
    }
    public static boolean verifyInput(String gs){
        String colorOptions = "RYGBWO";
        gs = gs.toUpperCase();
        // if the length is not equal to 4, return false
        if(gs.length() != 4) {
            return false;
        }
        // return true if the word is QUIT
        if(gs.equals("QUIT")) {
            return true;
        }
        // loop through the elements to check if the
        int totalCharactersFromTheUserThatAreInTheColorOptionsWeHave =0;
        for(int i= 0; i< gs.length(); i++) {
            for(int j = 0; j< colorOptions.length(); j++) {
                if(gs.charAt(i)== colorOptions.charAt(j)) {
                    totalCharactersFromTheUserThatAreInTheColorOptionsWeHave++;
                }
            }
        }
        // if we have all the characters in the gs string that matches our RYGBW, then
        // totalCharactersFromTheUserThatAreInTheColorOptionsWeHave will be 4;
        if(totalCharactersFromTheUserThatAreInTheColorOptionsWeHave == 4) {
            return  true;
        }
        return  false;
    }
}
