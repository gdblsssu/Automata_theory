package lab2;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Analyzer analyzer = new Analyzer();
        analyzer.readFile("test1.txt");
        analyzer.analyze("ex");
    }




}