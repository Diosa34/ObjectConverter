import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        @ClassAnnotation(value = "catalog", iterable = true)
        class Catalog extends ArrayList<Ticket>{
        }

        Catalog catalog = new Catalog();
        catalog.add(new Ticket(1, "Балет &apos;Щелкунчик&apos;", "15/01/2022", "19:00",
                "6000 руб.", "Бенуар", 4, 22));
        catalog.add(new Ticket(2, "Опера &apos;Иван Сусанин&apos;", "16/01/2022", "19:00",
                "6000 руб.", "Бенуар", 3, 20));


        Converter converter = new Converter();

        Path file = Paths.get("MyXML.xml");

        /** Создание списков строк для файлов различных форматов {@link Converter#reflection(Object)} */
        converter.reflection(catalog, 0);

        /** Запись в файлы соответствующих форматов {@link Converter#writeXML(Path)}*/
        converter.writeXML(file);
    }
}
