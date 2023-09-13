package lab1;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FSM fsm = new FSM();
        fsm.generate(4, 7);
        fsm.readFile("FSM-4-7.fsm");
        System.out.println(fsm.checkSeq("13"));
    }




}