import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, IOException {
        /** Поля класса, помеченные аннотациями содержат объекты, которые нужно конвертировать */
        class Instances {
            @ListAnnotation(value = "catalog", iterable = true)
            private List<Object> instances = new ArrayList();
            private Ticket ballet;
            @InstanceAnnotation(value = "element", iterable = false)
            private Ticket opera;

            Instances(){
                this.ballet = new Ticket(1, "Балет &apos;Щелкунчик&apos;", "15/01/2022", "19:00",
                        "6000 руб.", "Бенуар", 4, 22);
                this.opera = new Ticket(2, "Опера &apos;Иван Сусанин&apos;", "16/01/2022", "19:00",
                        "6000 руб.", "Бенуар", 3, 20);
                addInstances(ballet);
            }

            /** Добавление объектов в список, который будет обработан конвертером */
            public void addInstances(Object instance){
                instances.add(instance);
            }
        }

        Converter converter = new Converter("catalog");

        Path file = Paths.get("MyXML.xml");

        /** Создание списков строк для файлов различных форматов {@link Converter#reflection(Object)} */
        converter.reflection(new Instances(), false);

        /** Добавление корневого тега {@link Converter#addRootTag()} */
        converter.addRootTag();

        /** Запись в файлы соответствующих форматов {@link Converter#writeXML(Path)}*/
        converter.writeXML(file);
    }
}
