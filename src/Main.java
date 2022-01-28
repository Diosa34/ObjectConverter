import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        @ClassAnnotation(value = "catalog")
        class Catalog extends ArrayList<Convertible> implements Convertible{
        }

        Catalog catalog = new Catalog();
        catalog.add(new Ticket(1, "Балет &apos;Щелкунчик&apos;", "15/01/2022", "19:00",
                "6000 руб.", "Бенуар", 4, 22));
        catalog.add(new Ticket(2, "Опера &apos;Иван Сусанин&apos;", "16/01/2022", "19:00",
                "6000 руб.", "Бенуар", 3, 20));


        Converter converter = new Converter("MyXML.xml");

        /** Writing converted data to a file {@link Converter#xmlInitialization(Convertible, Integer)}*/
        converter.xmlInitialization(catalog, 0);
    }
}
