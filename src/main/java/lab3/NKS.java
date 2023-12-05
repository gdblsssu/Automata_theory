package lab3;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class NKS {
    List<Character> nonterminal;
    List<Character> terminal;
    ArrayList<String[]> rules = new ArrayList<>();
    Set<String> nterm = new HashSet<>();
    String res = "-";


    public NKS() {
        nonterminal = new ArrayList<>();
        terminal = new ArrayList<>();
    }

    public void grammarCheckWithRules(Set<String> nTerminals, ArrayList<String[]> rules, Set<String> sets, List<String> waysList, String grammar, String expression) {

        char ch = grammar.charAt(grammar.length() - 1);
        int index = grammar.length() - 1;
        boolean flag = true;

        if (!grammar.equals(expression)) {
            for (char charGrammar : grammar.toCharArray()) {
                if (nTerminals.contains(String.valueOf(charGrammar))) {
                    flag = false;
                }
            }
        }

        if (grammar.equals(expression)) {


            res = "+";

            return;
        }

        if (flag) {
            return;
        }

        while (true) {
            if (nTerminals.contains(String.valueOf(ch))) {
                break;
            } else {
                index--;
                ch = grammar.charAt(index);
            }
        }

        if (grammar.length() > expression.length() || sets.contains(grammar)) {
            return;
        }

        sets.add(grammar);

        for (int i = 0; i < rules.size(); i++) {
            if (rules.get(i)[0].equals(String.valueOf(ch))) {
                waysList.add("     " + grammar + " -> " + grammar.substring(0, index) + rules.get(i)[1] + grammar.substring(index + 1, grammar.length()));
                grammarCheckWithRules(nTerminals, rules, sets, waysList,grammar.substring(0, index) + rules.get(i)[1] + grammar.substring(index + 1, grammar.length()),expression);
                waysList.remove(waysList.size() - 1);
            }
        }
    }
    public void checkSeq(char startSign, String seq){
//        Rule res = new Rule(startSign, "");
//        boolean flag = false;
//        while(res.getLength() <= seq.length()){
//            if(flag == true) {
//                System.out.println("-");
//                return;
//            }
//            for(Rule rule: rules.get(res.getNonterminal())){
//                if(seq.startsWith(res.getTerminal() + rule.getTerminal()) &&
//                rule.getTerminal() != ""){
//                    if((res.getTerminal() + rule.getTerminal()).length() == seq.length() && rule.getNonterminal() == null){
//                        System.out.println("+");
//                        return;
//                    } else if((res.getTerminal() + rule.getTerminal()).length() == seq.length()) {
//                        flag = true;
//                        continue;
//                    }
//                    else{
//                        res.setTerminal(res.getTerminal() + rule.getTerminal());
//                        res.setNonterminal(rule.getNonterminal());
//                        flag = false;
//                        break;
//                    }
//
//                }
//            }
//        }


        grammarCheckWithRules(nterm, rules, new HashSet<String>(), new ArrayList<String>(), String.valueOf(startSign), seq);

        System.out.println(res);
    }

    public void readFile(String filename) throws IOException {
        File file = new File("/home/uiop/labs/Automati/lab1/src/main/java/lab3/input/" + filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        while (line != null){
            String[] splitLine = line.split(" ");
            nterm.add(splitLine[0]);
            rules.add(splitLine);
            line = bufferedReader.readLine();
        }




        bufferedReader.close();
        fileReader.close();
    }
}
