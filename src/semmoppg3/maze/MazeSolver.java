package maze;

import graphics.Svg;

import java.util.*;

/**
 * Created by knutandersstokke on 28 28.10.2017.
 */
public class MazeSolver {

    private final static String MAZE_FILE = "maze/maze.txt"; // or maze.txt

    /**
     * Solves the maze
     *
     * @return A list of points showing where the player needs to go to reach the end.
     * The points should be in the correct order, meaning the first point in the
     * list should be next to the player.
     */
    public static List<Point> solve(Maze m) {

        Set<Point> visited = new HashSet<>();
        Queue<Point> bfsQueue = new LinkedList<>();
        bfsQueue.add(m.getPlayer());
        HashMap<Point, Point> tracker = new HashMap<>();
        Set<Point> walls = m.getWalls();

        while (!bfsQueue.isEmpty()) {
            Point working = bfsQueue.poll();

            if (working.equals(m.getGoal()))
                break;

            for (Point p : getNeighbours(working))
                if (m.contains(p) && !walls.contains(p) && !visited.contains(p)) {
                    bfsQueue.add(p);
                    visited.add(p);
                    tracker.put(p, working);
                }
        }

        Point P = m.getGoal();
        List<Point> solution = new ArrayList<>();

        while (!P.equals(m.getPlayer())) {
            solution.add(0, P);
            P = tracker.get(P);
        }

        return solution;
    }

    public static ArrayList<Point> getNeighbours(Point p) {
        ArrayList<Point> res = new ArrayList<>();
        res.add(new Point(p.getX(), p.getY() + 1)); //North
        res.add(new Point(p.getX(), p.getY() - 1)); //South
        res.add(new Point(p.getX() - 1, p.getY())); //West
        res.add(new Point(p.getX() + 1, p.getY())); //East
        return res;
    }

    public static void main(String[] args) {
        Maze maze = new Maze(MAZE_FILE);
        String finalHtmlContent = Svg.buildSvgFromMaze(maze);
        Svg.runSVG(finalHtmlContent);
    }
}