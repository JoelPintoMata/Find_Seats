package com.findSeats;

/**
 * Main class for the FindSeats code challenge
 */
public class Main {

    /**
     * Main method for the FindSeats code challenge
     * @param args the initial set of arguments
     *             data source file path
     */
    public static void main(String[] args) {
        FindSeats findSeats = new FindSeats(args[0]);
        System.out.print(findSeats.toString());
    }
}