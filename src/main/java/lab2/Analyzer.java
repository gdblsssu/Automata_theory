package lab2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    List<List<String>> fileText;
    Dict dict;
    private final String RESERVE = "Зарезервированное слово";
    private final String SEPARATOR = "Разделитель";
    private final String IDENTIFIER = "Идентификатор";
    private final String CONSTANT = "Константа";
    private final String NALEXEME = "Неопознанная лексема";


    Analyzer(){
        fileText = new ArrayList<>();
        dict = new Dict();
    }

    public void analyze(String filename) throws IOException {
        File file = new File("/home/uiop/labs/Automati/lab1/src/main/java/lab2/texts/" + filename + ".anlz");
        FileWriter fileWriter = new FileWriter(file);
        List<List<Lexeme>> lexemeList = new ArrayList<>();

        defineSeparatorsReserve(lexemeList);
        defineIdentifier(lexemeList);
        defineConst(lexemeList);

        for(List<Lexeme> line: lexemeList){
            String str = "";
            for(Lexeme lexeme: line){
                str += lexeme.getValue() + " <" + lexeme.getType() + "> ";
            }
            str += "\n";
            fileWriter.write(str);
        }

        fileWriter.close();
    }

    private void defineSeparatorsReserve(List<List<Lexeme>> lexemeList){
        for(List<String> line: fileText) {
            List<Lexeme> tempList = new ArrayList<>();
            for (String val : line) {
                if (dict.separators.contains(val)) {
                    tempList.add(new Lexeme(val, SEPARATOR));
                } else if (dict.reserveWords.contains(val)) {
                    tempList.add(new Lexeme(val, RESERVE));
                } else {
                    boolean flag = false;
                    for (String sep : dict.separators) {
                        if (val.contains(sep)) {
                            val = val.replace(sep, "");
                            if(dict.reserveWords.contains(val)){
                                tempList.add(new Lexeme(val, RESERVE));
                            } else {
                                tempList.add(new Lexeme(val, NALEXEME));
                            }
                            tempList.add(new Lexeme(sep, SEPARATOR));
                            flag = true;
                            break;
                        }
                    }

                    if(!flag){
                        tempList.add(new Lexeme(val, NALEXEME));
                    }
                }
            }
            lexemeList.add(tempList);
        }
    }
    private void defineIdentifier(List<List<Lexeme>> lexemeList){
        String regex = "^[A-z]+\\w*";
        Pattern pattern = Pattern.compile(regex);
        for(List<Lexeme> line: lexemeList){
            for(int i = 0; i < line.size(); i++){
                Lexeme curLexeme = line.get(i);
                if(curLexeme.getType().equals(NALEXEME)){
                    Matcher matcher = pattern.matcher(curLexeme.getValue());
                    if(matcher.matches()){
                        if(i < line.size() - 1){
                            if(line.get(i + 1).getType().equals(SEPARATOR)){
                                curLexeme.setType(IDENTIFIER);
                            }
                        } else if(i > 0){
                            if(line.get(i - 1).getType().equals(SEPARATOR)){
                                curLexeme.setType(IDENTIFIER);
                            }
                        }
                    }
                }
            }
        }
    }

    private void defineConst(List<List<Lexeme>> lexemeList){
        for(List<Lexeme> line: lexemeList){
            for(int i = 0; i < line.size(); i++){
                Lexeme curLexeme = line.get(i);
                if(curLexeme.getType().equals(NALEXEME)){
                    if((curLexeme.getValue().charAt(0) == '"' &&
                            curLexeme.getValue().charAt(curLexeme.getValue().length() - 1) == '"') ||
                            curLexeme.getValue().chars().allMatch(Character :: isDigit)
                    ){
                        if(i > 0 && (line.get(i - 1).getType().equals(SEPARATOR)
                                || line.get(i - 1).getType().equals(RESERVE))){
                            curLexeme.setType(CONSTANT);
                        }
                    }
                }
            }
        }
    }

    public void readFile(String filename) throws IOException {
        File file = new File("/home/uiop/labs/Automati/lab1/src/main/java/lab2/texts/" + filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        fileText.clear();
        String line = bufferedReader.readLine();
        while(line != null){

            String[] splitLine = line.split(" ");
            List<String> temp = new ArrayList<>();
            for(String str: splitLine){
                if(str == "") continue;
                temp.add(str);
            }
            fileText.add(temp);
            line = bufferedReader.readLine();
        }

        bufferedReader.close();
        fileReader.close();
    }
}
