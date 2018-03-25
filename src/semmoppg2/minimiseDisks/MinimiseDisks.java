package minimiseDisks;

import helpers.FileReader;

import java.util.ArrayList;
import java.util.Arrays;

public class MinimiseDisks {

    public static void main(String[] args) {
        ArrayList<disk> disks = new ArrayList<>();

        int[] files = Arrays.stream(new FileReader().getFile("files.txt").split("\n"))
                .mapToInt(x -> Integer.parseInt(x.split("-")[1]))
                .sorted()
                .toArray();

        nextFile:
        for (int i = files.length - 1, diskNum = 1; i >= 0; i--) {
            disks.sort(disk::compareTo); //TODO: Merge instead... pq maby

            for (disk d : disks)
                if (d.getSize() >= files[i]) {
                    d.addFile(files[i]);
                    continue nextFile;
                }

            disk d = new disk("" + diskNum++);
            d.addFile(files[i]);
            disks.add(0,d);

        }

        for (disk x : disks)
            System.out.printf("%s %d %n", x, x.getSize());
    }

}

class disk implements Comparable<disk> {
    private int sizeLeft;
    private ArrayList<Integer> files;
    private String name;

    disk(String name) {
        sizeLeft = 1_000;
        files = new ArrayList<>();
        this.name = name;
    }

    void addFile(int filesize) {
        assert this.sizeLeft >= filesize;
        this.files.add(filesize);
        this.sizeLeft -= filesize;
    }

    int getSize() {
        return this.sizeLeft;
    }

    @Override
    public int compareTo(disk o) {
        return Integer.compare(this.getSize(), o.getSize());
    }

    @Override
    public String toString() {
        StringBuilder strbldr = new StringBuilder("Disk: " + name + ": [");
        for (int i = 0; i < this.files.size()-1; i++) {
            strbldr.append(this.files.get(i));
            strbldr.append(", ");
        }
        strbldr.append(this.files.get(this.files.size()-1));
        strbldr.append("]");

        return strbldr.toString();
    }
}
