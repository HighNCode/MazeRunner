package ca.master.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {
    @Test
    void getSizeX_Should_ReturnCorrectValue() {
        // Given
        List<List<Boolean>> mazeStructure = new ArrayList<>();
        mazeStructure.add(List.of(true, true, true));
        mazeStructure.add(List.of(true, false, true));
        mazeStructure.add(List.of(true, true, true));
        Position start = new Position(0, 0);
        Position end = new Position(2, 2);
        Maze maze = Maze.createMaze(mazeStructure, start, end);

        // When
        int sizeX = maze.getSizeX();

        // Then
        assertEquals(3, sizeX);
    }

    @Test
    void getSizeY_Should_ReturnCorrectValue() {
        // Given
        List<List<Boolean>> mazeStructure = new ArrayList<>();
        mazeStructure.add(List.of(true, true, true));
        mazeStructure.add(List.of(true, false, true));
        mazeStructure.add(List.of(true, true, true));
        Position start = new Position(0, 0);
        Position end = new Position(2, 2);
        Maze maze = Maze.createMaze(mazeStructure, start, end);

        // When
        int sizeY = maze.getSizeY();

        // Then
        assertEquals(3, sizeY);
    }

    @Test
    void isWall_Should_ReturnCorrectValue() {
        // Given
        List<List<Boolean>> mazeStructure = new ArrayList<>();
        mazeStructure.add(List.of(true, true, true));
        mazeStructure.add(List.of(true, false, true));
        mazeStructure.add(List.of(true, true, true));
        Position start = new Position(0, 0);
        Position end = new Position(2, 2);
        Maze maze = Maze.createMaze(mazeStructure, start, end);

        // When
        boolean isWall = maze.isWall(new Position(1, 1));

        // Then
        assertFalse(isWall);
    }

    @Test
    void validatePath_Should_ReturnFalse_When_InvalidPath() {
        // Given
        List<List<Boolean>> mazeStructure = new ArrayList<>();
        mazeStructure.add(List.of(true, true, true));
        mazeStructure.add(List.of(false, false, false));
        mazeStructure.add(List.of(true, true, true));
        Position start = new Position(0, 0);
        Position end = new Position(2, 2);
        Maze maze = Maze.createMaze(mazeStructure, start, end);
        Path invalidPath = new Path("FRFRFRFFFRF");

        // When
        boolean isValid = maze.validatePath(invalidPath);

        // Then
        assertFalse(isValid);
    }
}
