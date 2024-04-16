package ca.master.se2aa4.mazerunner;

import java.util.*;

public class MazeData {
    private final List<List<Boolean>> mazeStructure;
    private final Position start;
    private final Position end;

    public MazeData(List<List<Boolean>> mazeStructure, Position start, Position end) {
        this.mazeStructure = mazeStructure;
        this.start = start;
        this.end = end;
    }

    public List<List<Boolean>> getMazeStructure() {
        return mazeStructure;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }
}
