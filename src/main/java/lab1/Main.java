package lab1;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FSM fsm = new FSM();
        fsm.generate(6, 1);
        fsm.readFile("FSM-6-1.fsm");
        System.out.println(fsm.checkSeq("1"));



    }




}