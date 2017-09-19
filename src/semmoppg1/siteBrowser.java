package semmoppg1;

import java.util.Scanner;
import java.util.Stack;

public class siteBrowser {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Stack<String> backlog = new Stack<>();
        Stack<String> forwlog = new Stack<>();
        String curr = "";

        int N = scn.nextInt();
        scn.nextLine();

        for (int i = 0; i < N; i++) {
            String line = scn.nextLine();
            if (line.equals("*back*")) {
                if (!backlog.empty()) {
                    String page = backlog.pop();
                    forwlog.push(curr);
                    curr = page;
                } else {
                    System.out.println("[Warning-Oldest website]");
                    continue;
                }
            } else if (line.equals("*forward*")) {
                if (!forwlog.empty()) {
                    String page = forwlog.pop();
                    backlog.push(curr);
                    curr = page;
                } else {
                    System.out.println("[Warning-Furthest website]");
                    continue;
                }
            } else {
                backlog.add(curr);
                forwlog.clear();
                curr = line;
            }

            System.out.println(curr);
        }
    }
}