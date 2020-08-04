package com.example.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractParser {

    public abstract void action();
    public abstract void doParse();
    public abstract void clearBeforeStart();

    public void saveToFile(String path, List<Employee> employees) {
        File lf = new File(path);
        if (!(lf.exists() && lf.canRead() && lf.canWrite())) {
            try {
                new File(lf.getParent()).mkdirs();
                if (lf.createNewFile()) {
                    System.out.println("File created");
                } else {
                    return;
                }
            } catch (IOException e) {
                System.out.println("saveToFile: " + e.getMessage());
                return;
            }
        }
        try(FileOutputStream fos = new FileOutputStream(lf)) {
            String json = listToJson(employees);
            fos.write(json.getBytes());
        } catch (IOException e) {
            System.out.println("saveToFile: " + e.getMessage());
        }
    }

    public String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    public void clearFile(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles())
                clearFile(c);
        }
        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }

}
