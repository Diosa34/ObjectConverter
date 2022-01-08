import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Converter {
    ArrayList<String> xmlList = new ArrayList<>();

    public void reflection(Object instance, Integer indent) throws IllegalAccessException {
        /** Если в метод передали объект, не содержащий в себе другие объекты */
        String className = instance.getClass().getAnnotation(ClassAnnotation.class).value();
        xmlList.add(" ".repeat(indent)+"<" + className + ">");


        if (!instance.getClass().getAnnotation(ClassAnnotation.class).iterable()) {
            for (Field field : instance.getClass().getDeclaredFields()) {
                /** Если поле содержит данные, которые нужно записать в файл .xml, добавляем соответствующую строку
                 * в список строк для будущего файла */
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                if (fieldAnnotation != null) {
                    field.setAccessible(true);
                    xmlList.add("        <" + fieldAnnotation.value() + ">"
                            + field.get(instance) + "</" + fieldAnnotation.value() + ">");
                }
            }
        }

        /** Если в метод передали объект, содержащий в себе другие объекты */
        else {
            for (Object elem : (Iterable) instance) {
                /** Для каждого объекта добавляем тег-потомок и обрабатываем данный объект с помощью
                 *  {@link Converter#reflection(Object)} */
                reflection(elem, indent + 4);
            }
        }
        xmlList.add(" ".repeat(indent)+"</" + className + ">");
    }


    public void writeXML(Path file) throws IOException {
        Files.write(file, this.xmlList, StandardCharsets.UTF_8);
    }
}
