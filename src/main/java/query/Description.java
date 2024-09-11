package query;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import query.rules.Rule;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Description{

    private static ArrayList<Description> rules;
    private String name;
    private String desc;
    private String sugg;

    public Description(String name, String desc, String sugg) {
        this.name = name;
        this.desc = desc;
        this.sugg = sugg;
    }

    public Description(){

    }

    public Description(String name) {
        this.name = name;
    }

    public void loadJSON() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new FileReader("src/main/java/resource/data_1.json"));
        JSONObject jsonObject = (JSONObject) obj;

        rules = new ArrayList<Description>();
        for (int i = 1; i < 12; i++) {
            JSONArray array = (JSONArray) jsonObject.get("rule"+i);
            JSONObject rule = (JSONObject) array.get(0);
            rules.add(new Description((String) rule.get("name"), (String) rule.get("desc"), (String) rule.get("sgs")));
        }
    }

    public static ArrayList<Description> getRules() {
        return rules;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getSugg() {
        return sugg;
    }


}
