package com.example.bruce.snake_startercode;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeSegmentTest {

    SnakeSegment[] tests = {new SnakeSegment(SnakeSegment.BodyPart.HEAD, 0, 10, 20),
            new SnakeSegment(SnakeSegment.BodyPart.BODY, 90, 15, 30),
            new SnakeSegment(SnakeSegment.BodyPart.BODY, 180, 18, 36),
            new SnakeSegment(SnakeSegment.BodyPart.TAIL, 270, 19, 38)};

    @Test
    public void getBodyPart() {
        SnakeSegment.BodyPart[] input = {SnakeSegment.BodyPart.HEAD, SnakeSegment.BodyPart.BODY, SnakeSegment.BodyPart.BODY, SnakeSegment.BodyPart.TAIL};
        SnakeSegment.BodyPart[] output = new SnakeSegment.BodyPart[4];
        for (int i = 0; i < tests.length; i++) output[i] = tests[i].getBodyPart();
        assertArrayEquals(input, output);
    }

    @Test
    public void getDegrees() {
        int[] input = {0, 90, 180, 270};
        int[] output = new int[4];
        for (int i = 0; i < tests.length; i++) output[i] = tests[i].getDegrees();
        assertArrayEquals(input, output);
    }

    @Test
    public void setDegrees() {
        tests[0].setDegrees(90);
        assertTrue(tests[0].getDegrees() == 90);
    }

    @Test
    public void getXLoc() {
        int[] input = {10, 15, 18, 19};
        int[] output = new int[4];
        for (int i = 0; i < tests.length; i++) output[i] = tests[i].getXLoc();
        assertArrayEquals(input, output);
    }

    @Test
    public void setXLoc() {
        tests[0].setXLoc(100);
        assertTrue(tests[0].getXLoc() == 100);
    }

    @Test
    public void getYLoc() {
        int[] input = {20, 30, 36, 38};
        int[] output = new int[4];
        for (int i = 0; i < tests.length; i++) output[i] = tests[i].getYLoc();
        assertArrayEquals(input, output);
    }

    @Test
    public void setYLoc() {
        tests[0].setYLoc(100);
        assertTrue(tests[0].getYLoc() == 100);
    }
}