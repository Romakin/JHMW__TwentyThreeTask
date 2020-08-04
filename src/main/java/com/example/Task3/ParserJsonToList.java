package com.example.Task3;

import com.example.Utils.Employee;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ParserJsonToList {

    public void action() {
        doParse();
    }

    private void doParse() {
        String content = readString("./data2.json");
        List<Employee> list = jsonToList(content);
        list.stream().forEach(System.out::println);
    }

    private String readString(String path) {
        String result = "";
        try(FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) { }
        return result;
    }

    private List<Employee> jsonToList(String json) {
        // Функция реализована согласно заданию
        //  но на больших объемах данных будет значительно проседать по производительсти
        List<Employee> result = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        JSONParser jParser = new JSONParser();
        try {
            JSONArray jsonArray = (JSONArray) jParser.parse(json);
            for (int io = 0; io < jsonArray.size(); io++) {
                result.add(gson.fromJson(((JSONObject) jsonArray.get(io)).toJSONString(), Employee.class));
            }
        } catch (Exception e) {}
        return result;
    }

    private List<Employee> jsonToListNormal(String json) {
        List<Employee> result = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        ArrayList<LinkedTreeMap> bufArr = gson.fromJson(json, ArrayList.class);
        for (LinkedTreeMap ltm : bufArr) {
            result.add(new Employee(
                    ((Double) ltm.get("id")).longValue(),
                    (String) ltm.get("firstName"),
                    (String) ltm.get("lastName"),
                    (String) ltm.get("country"),
                    ((Double) ltm.get("age")).intValue()
            ));
        }
        return result;
    }

}
