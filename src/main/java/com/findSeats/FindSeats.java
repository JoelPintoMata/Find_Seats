package com.findSeats;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * FindSeats solution generator class
 *
 * This class generates a seats configuration for a given airplane configuration and group of travellers
 * while taking into account their preferences to fly in group and near a window seat
 */
public class FindSeats {

    private String[] seats;
    private List<String> groups = new LinkedList<>();

    private int rows;
    private int columns;
    private int totalTravellers;

    /**
     * FindSeats constructor
     * @param sourceFilePath the data source file path
     */
    public FindSeats(String sourceFilePath) {
        String string;
        try (FileReader fileReader = new FileReader(sourceFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader)){

//            read the first line specifying the number of rows and columns
            if(!(string = bufferedReader.readLine()).isEmpty()){
                String[] rowsXcolumns = string.split(" ");
                rows = Integer.parseInt(rowsXcolumns[0]);
                columns = Integer.parseInt(rowsXcolumns[1]);
                seats = initializeSeats(rows, columns);
            }
//            read the groups
            while((string = bufferedReader.readLine()) != null){
                totalTravellers = totalTravellers + string.split(" ").length;
                groups.add(string.replace(" ", ","));
            }
            seats = generateSolution(seats, groups);
        } catch (FileNotFoundException e) {
            System.out.println("No solution found: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("No solution found: "+e.getMessage());
        }
    }

    /**
     * Initializes the seats array
     *
     * The seats array is initialized with "-1" is limited, on both the right and left side, by a "#" character
     *
     * @param rows the number of plane rows
     * @param columns the number of plane columns
     * @return a initialized seats array of strings
     */
    private static String[] initializeSeats(int rows, int columns) {
        String[] seats = new String[rows+1];
        StringBuilder stringBuilder = new StringBuilder("#");
        for(int i=0; i<columns; i++){
            stringBuilder.append(",-1");
        }
        stringBuilder.append(",#");
        String string = stringBuilder.toString();
        for(int j=0; j<rows; j++)
            seats[j] = string;
        seats[seats.length-1] = "0";
        return seats;
    }

    /**
     * Recursive method responsible for finding the correct seat configuration for all the given groups
     * @param seatsAux the current seat configuration/occupancy
     * @param groups the groups left to be seated
     * @return the final seat configuration array of strings
     */
    private String[] generateSolution(String[] seatsAux, List<String> groups) {
//        keep the best configuration until this moment
        if(Integer.parseInt(seatsAux[seatsAux.length-1]) > Integer.parseInt(seats[seats.length-1]))
            seats = seatsAux;
//        check if we already seated all the groups
        if(groups.size() == 0)
            return seats;
        List<String[]> solutions = findPossibleSeatsConfigurations(seatsAux, groups.get(0));
        if(solutions.size() == 0) {
            fillInSolution(seatsAux, groups.get(0));
            groups.remove(0);
        } else {
            groups.remove(0);
            solutions.forEach(solution -> {
                generateSolution(solution, groups);
            });
        }
        return seats;
    }

    /**
     * Randonly fills the optimal seats configuration with the rest of travellers
     * @param seatsAux a optimal seats configuration
     * @param group
     */
    private String[] fillInSolution(String[] seatsAux, String group) {
        String row;
        String[] travellers = group.split(",");
        for(String traveller:travellers){
            for(int i=0; i<seatsAux.length; i++){
                row = seatsAux[i];
                if(row.indexOf("-1") > 0) {
//                    TODO: to fix?
//                      being that i cannot sit the group whole togehter i assume that there is no
//                      customer satisfaction to be gained by seating a single passanger near the window
                    seatsAux[i] = row.replaceFirst("-1", traveller);
//                    lets try to seat the next traveller
                    break;
                }
            }
        }
        return seatsAux;
    }

    /**
     * Finds and returns all possible seats configuration
     * @param seats the seat configuration
     * @param group the group size and configuration preferences (window, no window)
     * @return a array containing all possible solution combinations
     */
    private List<String[]> findPossibleSeatsConfigurations(String[] seats, String group) {
        String[] groupArray = group.split(",");
        StringBuilder aux = new StringBuilder();
        String stringAux;
        String[] seatsAux;
        List<String[]> solutions = new LinkedList<>();

//        builds a representation of an empty space for this group size
        for(int i=0; i<groupArray.length; i++)
            aux = aux.append("-1,");

//        check for a passenger requesting window seat
//        check for a window in the left side
        seatsAux = seats.clone();
        if(groupArray[0].indexOf("W") > 0) {
            aux = new StringBuilder("#,").append(aux);
            stringAux =  aux.toString().substring(0, aux.toString().length() - 1);
            for(int j=0; j<seatsAux.length; j++){
                if (seatsAux[j].startsWith(stringAux)) {
                    seatsAux[j] = seatsAux[j].replaceFirst(stringAux, group);
                    seatsAux[seats.length-1] = String.valueOf(Integer.parseInt(seats[seats.length-1]) + group.split(",").length);
                    solutions.add(seatsAux);
                    break;
                }
            }

//        check for a passenger requesting window seat
//        check for a window in the right side
        }
        seatsAux = seats.clone();
        if(groupArray[groupArray.length-1].indexOf("W") > 0) {
            stringAux = aux.toString();
            stringAux = stringAux.substring(0, stringAux.length() - 1) + ",#";
            for (int j = 0; j < seatsAux.length; j++) {
                if (seatsAux[j].endsWith(stringAux)) {
                    seatsAux[j] = seatsAux[j].replace(stringAux, group);
                    seatsAux[seats.length-1] = String.valueOf(Integer.parseInt(seats[seats.length-1]) + group.split(",").length);
                    solutions.add(seatsAux);
                    break;
                }
            }
        }

//        this group was no preference regarding window
        seatsAux = seats.clone();
        if(groupArray[groupArray.length-1].indexOf("W") < 0) {
            for (int j = 0; j < seatsAux.length; j++) {
                stringAux = aux.toString();
                stringAux = stringAux.substring(0, stringAux.length() - 1);
                if (seatsAux[j].contains(stringAux)) {
                    seatsAux[j] = seatsAux[j].replaceFirst(stringAux, group);
                    seatsAux[seats.length-1] = String.valueOf(Integer.parseInt(seats[seats.length-1]) + group.split(",").length);
                    solutions.add(seatsAux);
                    break;
                }
            }
        }
        return solutions;
    }

    /**
     * Returns the final seat configuration
     *
     * @return a, previously calculated, final seat configuration array of strings
     */
    public String[] getSolution() {
        String[] solution = new String[0];
        if(seats != null && seats.length > 0) {
            solution = seats.clone();
            for (int i = 0; i < seats.length; i++) {
                solution[i] = seats[i].replace("W", "").replace("#,", "").replace(",#", "");
            }
            solution[solution.length-1] = (((Integer.parseInt(solution[solution.length-1]) * 100) / (totalTravellers)) + "%");
        }
        return solution;
    }

    @Override
    public String toString(){
        String[] solution = getSolution();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<solution.length; i++){
            stringBuilder.append(solution[i]).append("\n");
        }
        return stringBuilder.toString();
    }
}