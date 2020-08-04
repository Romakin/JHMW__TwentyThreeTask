package com.example.Task1;

import com.example.Utils.*;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserCsvToJson extends AbstractParser {

    public void action() {
        clearBeforeStart();
        doParse();
    }

    public void doParse() {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String filePath = "./data.csv";
        List<Employee> apacheList = parseCSV_apache(filePath, columnMapping);
        List<Employee> opencsvList = parseCSV_opencsv(filePath, columnMapping);
        saveToFile("./data_apache.json", apacheList);
        saveToFile("./data_opencsv.json", opencsvList);
    }

    public List<Employee> parseCSV_apache(String filePath, String[] headers) {
        List<Employee> result = new ArrayList<>();
        try (Reader in = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(headers)
                    .parse(in);
            System.out.println(Employee.class.getDeclaredFields()[0].getName());
            for (CSVRecord record : records) {
                result.add(
                        new Employee(
                                Long.parseLong(record.get("id")),
                                record.get("firstName"),
                                record.get("lastName"),
                                record.get("country"),
                                Integer.parseInt(record.get("age"))
                        )
                );
            }

        } catch (IOException ex) {
            System.out.println("parseCSV_apache: " + ex.getMessage());
            return null;
        }

        return result;
    }

    public List<Employee> parseCSV_opencsv(String filePath, String[] headers) {
        List<Employee> result = new ArrayList<>();
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(headers);
        try (Reader in = new FileReader(filePath);
             CSVReader csvReader = new CSVReader(in)) {
            CsvToBean csv = new CsvToBeanBuilder(csvReader)
                    .withMappingStrategy(strategy).build();
            List list = csv.parse();
            for (Object object : list) {
                result.add((Employee) object);
            }
        } catch (IOException ex) {
            System.out.println("parseCSV_opencsv: " + ex.getMessage());
            return null;
        }

        return result;
    }

    public void clearBeforeStart() {
        try {
            clearFile(new File("./data_apache.json"));
        } catch (Exception e) {}
        try {
            clearFile(new File("./data_opencsv.json"));
        } catch (Exception e) {}
    }

}
