package lab3;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        NKS nks = new NKS();
        nks.readFile("grammar.gr");
        nks.checkSeq("10111010");
        int a = 5;
    }
}
