import com.example.Task1.ParserCsvToJson;
import com.example.Task2.ParserXmlToJson;
import com.example.Task3.ParserJsonToList;

public class Main {

    public static void main(String[] args) {
        (new ParserCsvToJson()).action();
        (new ParserXmlToJson()).action();
        (new ParserJsonToList()).action();
    }

}
