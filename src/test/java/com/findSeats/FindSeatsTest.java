package com.findSeats;

import org.junit.Assert;
import org.junit.Test;

/**
 * FindSeats code challenge testing class
 */
public class FindSeatsTest {

    /**
     * Tests the results against a well defined solution
     * In this scenario we aim to test a best case scenario
     */
    @Test
    public void solution1Test(){
        String[] targetSeats = new String[]{"1,2,3,8", "4,5,6,7", "12,9,10,11", "13,14,15,16", "100%"};
        FindSeats findSeats = new FindSeats("src/test/resources/test1.txt");
        Assert.assertEquals(findSeats.getSolution(), targetSeats);
    }

    /**
     * Tests the results against a well defined solution
     * In this scenario we aim to test a best case scenario
     */
    @Test
    public void solution2Test(){
        String[] targetSeats = new String[]{"1,2,3,8", "4,5,6,7", "12,9,10,11", "13,14,15,16", "100%"};
        FindSeats findSeats = new FindSeats("src/test/resources/test2.txt");
        Assert.assertEquals(findSeats.getSolution(), targetSeats);
    }

    /**
     * Tests the results against a well defined solution
     * In this scenario we aim to test a best case scenario, that is, not a full occupancy
     */
    @Test
    public void solution3Test(){
        String[] targetSeats = new String[]{"1,2,3,8", "4,5,6,7", "15,9,10,11", "12,13,14,16", "87%"};
        FindSeats findSeats = new FindSeats("src/test/resources/test3.txt");
        Assert.assertEquals(findSeats.getSolution(), targetSeats);
    }

    /**
     * Tests that no exceptions are raised even when given a invalid source file
     * In this scenario we aim to test a empty solution
     */
    @Test
    public void invalidsourceFileTest(){
        String[] targetSeats = new String[]{};
        FindSeats findSeats = new FindSeats("src/test/resources/no_existing_file.txt");
        Assert.assertEquals(findSeats.getSolution(), targetSeats);
    }

    /**
     * Tests the results against a well defined solution
     * In this scenario we aim to test a scenario where all groups window preferences are respected
     */
    @Test
    public void solution4Test(){
        String[] targetSeats = new String[]{"1,2,3,-1", "-1,4,5,6", "-1,-1,-1,-1", "-1,-1,-1,-1", "100%"};
        FindSeats findSeats = new FindSeats("src/test/resources/test4.txt");
        Assert.assertEquals(findSeats.getSolution(), targetSeats);
    }
}