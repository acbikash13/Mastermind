import java.io.*;
import java.net.*;
import java.util.Random;

public class gameThread  extends Thread{
    private Socket client;
    String randomSecretColorCharacters;
    gameThread(Socket client) {
        this.client = client;
    }
    public void run() {
        DataInputStream inputStreamFromTheClient;
        DataOutputStream outputStreamToTheClient;
        try {
            // set up input and the output streams for the socket it has received.
            inputStreamFromTheClient = new DataInputStream(client.getInputStream());
            outputStreamToTheClient = new DataOutputStream(client.getOutputStream());
            System.out.println("Successfully created the input nad output stream to read from the client and to send to the client! ");
            //Randomly generate a Random 4 character code that consits of only the 6 letters (R, Y, G, B, W, O)
            randomSecretColorCharacters = generateFourCharacter();

            // maybe use stringBuilers? Need to check this later if any problems seen.
            String messageToSendToTheClient = "";
            int keepTrackOfTheNumberOfTurns = 0;
            // while we have not found the matching characters
            while(!messageToSendToTheClient.equalsIgnoreCase("PPPP") && keepTrackOfTheNumberOfTurns <=20) {
                if(keepTrackOfTheNumberOfTurns ==0) {
                    outputStreamToTheClient.writeUTF("GO");
                    System.out.println("GO sent to the client!");
                    keepTrackOfTheNumberOfTurns++;
                }
                else {
                    String messageReceivedFromTheClient = inputStreamFromTheClient.readUTF();
                    System.out.println("The color combination received fromt the client is " + messageReceivedFromTheClient);
                    if(messageReceivedFromTheClient.equalsIgnoreCase("QUIT")) {
                        // client wants to exit the program.
                        return;
                    }
                    messageToSendToTheClient = processGuess(messageReceivedFromTheClient);
                    keepTrackOfTheNumberOfTurns++;
                    // send the message received from the processGuess() function to the client.
                    outputStreamToTheClient.writeUTF(messageToSendToTheClient);
                }
            }
        }
        catch (IOException e) {
            System.out.println("IOException on socket listen: " + e);
            e.printStackTrace();
        }
    }
    // generate a random four character string from the 'R,Y,G,B,W,O' characters
    public String generateFourCharacter(){
        Random random = new Random();
        // this will be our string to return, Using string builders instead of Strings as strings are immutable. Can go for Strings as well.
        StringBuilder secretCharactersForOurGame = new StringBuilder();
        String colorsToIncludeInOurSecretCode = "RYGBWO";
        for (int i = 0; i < 4; i++) {
            secretCharactersForOurGame.append(colorsToIncludeInOurSecretCode.charAt(random.nextInt(colorsToIncludeInOurSecretCode.length())));
        }
        // testing the randomly generated secret color code.
        // returninh the secret code as strings.
        System.out.println("Randomly generated secret Color Code is : " + secretCharactersForOurGame.toString());
        return secretCharactersForOurGame.toString();
    }

    //might need to use the stringBuilder as they are mutable instead of the strings.
    String processGuess(String guessStringColorReceivedFromTheClient) {
        StringBuilder messageToSendToTheClient= new StringBuilder();
        for(int indexOfTheColor=0; indexOfTheColor<4;indexOfTheColor++){
            //if the character of the guessed color and secret color matches and are in the same place we add P
            if(guessStringColorReceivedFromTheClient.charAt(indexOfTheColor)== this.randomSecretColorCharacters.charAt(indexOfTheColor)){
                messageToSendToTheClient.append("P");
                System.out.println("Our string to send to the client is " + messageToSendToTheClient);
            }
            //guessStringColorReceivedFromTheClient.charAt(indexOfTheColor) this gives us the character from our guess string to compare to the secret string
            // and passing this value to indexOf return an idex of the char if it exists and -1 if it does not exist. Since we are checking for the correct position as well in the above if block,
            // this block checks for the matvching elements.
            else if (this.randomSecretColorCharacters.indexOf(guessStringColorReceivedFromTheClient.charAt(indexOfTheColor)) != -1) {
                messageToSendToTheClient.append("C");
                System.out.println("Our string to send to the client is " + messageToSendToTheClient);
            }
        }
        return messageToSendToTheClient.toString();
        // fails to work on the cases where there are multipl same colors.
//        int numberOfMatchesFound = -1;
//        for(int indexOfUserGuessedColor = 0; indexOfUserGuessedColor < 4; indexOfUserGuessedColor++){
//            for(int indexOfSecretColor = 0; indexOfSecretColor < 4; indexOfSecretColor++){
//                // if the index and the color matches, there will be  a P in the string Server would send to the client
//                if(this.randomSecretColorCharacters.charAt(indexOfSecretColor) == guessStringColorReceivedFromTheClient.charAt(indexOfUserGuessedColor) &&indexOfSecretColor == indexOfUserGuessedColor) {
//                    if(indexOfUserGuessedColor > numberOfMatchesFound ) {
//                        messageToSendToTheClient += "P";
//                        numberOfMatchesFound++;
//                        System.out.println("Our code to send to the CLient " + messageToSendToTheClient);
//                        break;
//                    }
//                }
//                else if(this.randomSecretColorCharacters.charAt(indexOfSecretColor) == guessStringColorReceivedFromTheClient.charAt(indexOfUserGuessedColor)){
//                    if(indexOfUserGuessedColor > numberOfMatchesFound ) {
//                        messageToSendToTheClient += "C";
//                        numberOfMatchesFound++;
//                        System.out.println("Our code to send to the CLient " + messageToSendToTheClient);
//                        break;
//                    }
//                }
//            }
//
//        }
    }
}
