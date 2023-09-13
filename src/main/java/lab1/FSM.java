package lab1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FSM {
    private char countState;
    private char beginState;
    private char endState;
    List<List<Character>> arrStates = new ArrayList<>();


    public String checkSeq(String str){
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            boolean flag = false;
            for(List<Character> arr: arrStates){
                if(arr.get(0) == countState && arr.get(2) == ch){
                    countState = arr.get(1);
                    flag = true;
                    break;
                }
            }
            if(!flag) return "Последовательность не подходит";
        }
        if(countState == endState) return "Число делится";

        return "Число не делится";
    }

    public void generate(int P, int Q) throws IOException {
        String alph = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String beState = "0 0";
        Character end = 0;
        List<List<Character>> generatedState = new ArrayList<>();
        for(int i = 0; i < Q; i++){
            for(int j = 0; j < P; j++){
                List<Character> temp = new ArrayList<>();
                temp.add(alph.charAt(i));
                temp.add(alph.charAt(end));
                temp.add(alph.charAt(j));
                if(end == Q - 1){
                    end = 0;
                } else end++;
                generatedState.add(temp);
            }
        }

        File file = new File("FSM-" + P + "-" + Q + ".fsm");
        FileWriter fw = new FileWriter(file);
        fw.write(beState + "\n");
        for(List<Character> list: generatedState){
            String temp = "";
            for(char ch: list){
                temp += ch + " ";
            }
            temp = temp.substring(0, temp.length() - 1);
            fw.write(temp + "\n");
        }
        fw.close();

    }

    public void readFile(String filename){

        try {
            File file = new File(filename);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            List<List<Character>>  arr = new ArrayList<>();
            String line = reader.readLine();
            String[] beStates = line.split(" ");
            line = reader.readLine();
            while (line != null){
                String[] state = line.split(" ");
                List<Character> temp = new ArrayList<>();
                temp.add(state[0].toCharArray()[0]);
                temp.add(state[1].toCharArray()[0]);
                temp.add(state[2].toCharArray()[0]);
                arr.add(temp);
                line = reader.readLine();
            }
            countState = beStates[0].toCharArray()[0];
            beginState = beStates[0].toCharArray()[0];
            endState = beStates[1].toCharArray()[0];
            arrStates = arr;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
