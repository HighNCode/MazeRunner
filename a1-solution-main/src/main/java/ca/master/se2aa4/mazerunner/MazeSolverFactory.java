package ca.master.se2aa4.mazerunner;

public class MazeSolverFactory {

    public static MazeSolver createSolver(String method) throws Exception {
        switch (method) {
            case "righthand":
                return new RightHandSolver();
            case "tremaux":
                return new TremauxSolver();
            case "dijkstra":
                return new DijkstraSolver();
            default:
                throw new IllegalArgumentException("Maze solving method '" + method + "' not supported.");
        }
    }
}
