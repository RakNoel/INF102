package maze;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Maze {


    private Set<Point> walls = new HashSet<>();
    private Point player;
    private Point goal;
    private int width, height;

    public Maze(Set<Point> walls, Point player, Point goal, int width, int height) {
        this.walls = walls;
        this.player = player;
        this.goal = goal;
        this.width = width;
        this.height = height;
    }

    public Maze(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream resource = classloader.getResourceAsStream(fileName)) {
            if (resource == null) {
                throw new IllegalArgumentException("File is missing");
            }
            List<String> lines = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.toList());
            int[] dimensions = Arrays.stream(lines.get(0).split(" ")).mapToInt(Integer::parseInt).toArray();
            width = dimensions[0];
            height = dimensions[1];
            lines.remove(0);
            for (int y = 0; y < lines.size(); y++) {
                char[] chars = lines.get(y).toCharArray();
                for (int x = 0; x < chars.length; x += 2) {
                    if (chars[x] == '#')
                        walls.add(new Point(x / 2, y));
                    else if (chars[x] == 'S')
                        player = new Point(x / 2, y);
                    else if (chars[x] == 'E') {
                        goal = new Point(x / 2, y);
                    }
                }
            }
            if (player == null)
                throw new IllegalStateException("Maze has no start position.");
            else if (goal == null)
                throw new IllegalStateException("Maze has no end position.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Set<Point> getWalls() {
        return walls;
    }

    public Point getPlayer() {
        return player;
    }


    public Point getGoal() {
        return goal;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Point p) {
        return (p.getX() >= 0 && p.getX() < this.getWidth() && p.getY() >= 0 && p.getY() < this.getHeight());
    }


}