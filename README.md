
# Mastermind Game - Multithreaded Client-Server Application

## Overview

This project implements a multithreaded client-server application to play the game Mastermind. The server generates a random 4-color code, and the client attempts to guess the sequence within 20 attempts. The server provides feedback on the correctness of each guess.

## Game Description

Mastermind is a code-breaking game where the player tries to guess a secret code consisting of 4 colored pegs. Each peg can be one of six colors: Red (R), Yellow (Y), Green (G), Blue (B), White (W), or Orange (O). The server provides feedback on each guess using:
- **P:** Correct color and position.
- **C:** Correct color but wrong position.
- **Blanks:** Incorrect color and position.

The game continues until the player correctly guesses the code or exhausts 20 attempts.

## File Structure

- `gameDaemon.java`: Main class that sets up the server, listens for connections, and spawns game threads.
- `gameThread.java`: Handles individual game sessions, generating codes, processing guesses, and providing feedback.
- `mastermind.java`: Client program that connects to the server, sends guesses, and displays feedback.

## How to Run

### Server

1. Compile `gameDaemon.java` and `gameThread.java`.
   ```sh
   javac gameDaemon.java gameThread.java
   ```
2. Run the server.
   ```sh
   java gameDaemon
   ```

### Client

1. Compile `mastermind.java`.
   ```sh
   javac mastermind.java
   ```
2. Run the client.
   ```sh
   java mastermind
   ```

## Sample Execution

### Sample Execution #1 (Code: RGGB)

```
Welcome to Mastermind. You will try to guess a 4 color code using
only the letters ( R, Y, G, B, W, O). You will lose the game if you
are unable to guess the code correctly in 20 guesses. Good Luck!

Please enter your guess for the secret code or “QUIT”: BBGG
BBGG CCP
Please enter your guess for the secret code or “QUIT”: GBBY
GBBY CC
Please enter your guess for the secret code or “QUIT”: GRBG
GRBG CCCC
Please enter your guess for the secret code or “QUIT”: BGGR
BGGR CCPP
Please enter your guess for the secret code or “QUIT”: RGGB
RGGB PPPP

Congratulations!!! You guessed the code correctly in 5 moves
```

### Sample Execution #2 (Code: BGWG)

```
Welcome to Mastermind. You will try to guess a 4 color code using
only the letters ( R, Y, G, B, W, O). You will lose the game if you
are unable to guess the code correctly in 20 guesses. Good Luck!

Please enter your guess for the secret code or “QUIT”: BBGW
BBGG CCP
Please enter your guess for the secret code or “QUIT”: GBBY
GBBY CC
Please enter your guess for the secret code or “QUIT”: QUIT

Goodbye but please play again!
```
