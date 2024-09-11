package query.rules;

import java.util.ArrayList;

public class TextAreaParse {
    public ArrayList<String> parseTable(String[] textArea) {
        ArrayList<String> imeTabele = new ArrayList<>();
        for (int i = 0; i < textArea.length; i++) {
            if (textArea[i].equalsIgnoreCase("FROM") || textArea[i].equalsIgnoreCase("JOIN")) {
                textArea[i + 1] = textArea[i + 1].replaceAll("bp_tim90.", "");
                imeTabele.add(textArea[i + 1]);
            }
        }
        return imeTabele;
    }

    public ArrayList<String> parseColumnAfterSelect(String[] textArea) {
        ArrayList<String> imenaKolona = new ArrayList<>();
        for (int i = 0; i < textArea.length; i++) {
            if(textArea[i].equalsIgnoreCase("SELECT")) {
                i++;
                if(textArea[i].equalsIgnoreCase("*")) {
                    imenaKolona.add("*");
                    return imenaKolona;
                }
                while(!textArea[i].equalsIgnoreCase("FROM")) {
                    String parse = textArea[i].replaceAll(",", "");
                    imenaKolona.add(parse);
                    i++;
                }
            }
        }
        for(int i = 0; i<imenaKolona.size(); i++){
            imenaKolona.set(i, imenaKolona.get(i).replaceAll(" ", ""));
        }
        return imenaKolona;
    }
    public ArrayList<String> parseColumnAfterOn(String [] textArea){
        ArrayList<String> parse = new ArrayList<>();
        for (int i = 0; i < textArea.length; i++) {
            if (textArea[i].equalsIgnoreCase("on"))
            {
                do{
                    i++;
                    parse.add(textArea[i].replaceAll("[(,)]", ""));
                }while(!textArea[i].contains(")"));
                parse.remove("=");
                for(int j = 0; j< parse.size(); j++){
                    String [] flag = parse.get(j).split("[.]");
                    if(flag.length == 2){
                        parse.set(j, flag[1]);
                    }
                }
            }
        }
        return parse;
    }
}
