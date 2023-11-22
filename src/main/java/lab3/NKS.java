package lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NKS {
    List<Character> nonterminal;
    List<Character> terminal;
    char startSign;
    Map<Character, List<Rule>> rules;

    public NKS() {
        nonterminal = new ArrayList<>();
        terminal = new ArrayList<>();
        rules = new HashMap<>();
    }

    public void checkSeq(String seq){
        Rule res = new Rule(startSign, "");
        boolean flag = false;
        while(res.getLength() <= seq.length()){
            if(flag == true) {
                System.out.println("-");
                return;
            }
            for(Rule rule: rules.get(res.getNonterminal())){
                if(seq.startsWith(res.getTerminal() + rule.getTerminal())){
                    if((res.getTerminal() + rule.getTerminal()).length() == seq.length() && rule.getNonterminal() == null){
                        System.out.println("+");
                        return;
                    } else if((res.getTerminal() + rule.getTerminal()).length() == seq.length()) {
                        flag = true;
                        continue;
                    }
                    else{
                        res.setTerminal(res.getTerminal() + rule.getTerminal());
                        res.setNonterminal(rule.getNonterminal());
                        flag = false;
                        break;
                    }

                }
            }
        }
        if(res.getTerminal().equals(seq) && res.getLength() == seq.length()) System.out.println("+");
        else System.out.println("-");

    }

    public void readFile(String filename) throws IOException {
        File file = new File("/home/uiop/labs/Automati/lab1/src/main/java/lab3/input/" + filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        String[] splitLine = line.split(" ");
        for(String s: splitLine){
            terminal.add(s.toCharArray()[0]);
        }
        line = bufferedReader.readLine();
        splitLine = line.split(" ");
        for(String s: splitLine){
            nonterminal.add(s.toCharArray()[0]);
        }
        line = bufferedReader.readLine();
        splitLine = line.split(" ");
        startSign = splitLine[0].toCharArray()[0];
        line = bufferedReader.readLine();
        while(line != null){
            splitLine = line.split(" ");
            Character left = splitLine[0].toCharArray()[0];
            String right = splitLine[1];
            if(!rules.containsKey(left)){
                rules.put(left, new ArrayList<>());
            }
            Rule rule;
            if(Character.isDigit((right.charAt(right.length() - 1)))){
                rule = new Rule(null, right);
            } else {
                rule = new Rule(right.charAt(right.length() - 1), right.substring(0, right.length() - 1));
            }
            rules.get(left).add(rule);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();
    }
}
