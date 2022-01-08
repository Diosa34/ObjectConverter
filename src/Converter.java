import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Converter {
    ArrayList<String> xmlList = new ArrayList<>();
    String rootTag;

    Converter(String rootTag){
        this.rootTag = rootTag;
    }

    public void reflection(Object instance, boolean iterable) throws IllegalAccessException {
        /** Если в метод передали объект, не содержащий в себе другие объекты */
        if (!iterable) {
            for (Field field : instance.getClass().getDeclaredFields()) {
                /** Если поле содержит список, вызывается данный метод, а этот список передаётся как аргумент */
                ListAnnotation listAnnotation = field.getAnnotation(ListAnnotation.class);
                if (listAnnotation != null) {
                    field.setAccessible(true);
                    reflection(field.get(instance), listAnnotation.iterable());
                }

                /** Если поле содержит объект класса, вызывается данный метод, а этот объект передаётся как аргумент */
                InstanceAnnotation instanceAnnotation = field.getAnnotation(InstanceAnnotation.class);
                if (instanceAnnotation != null) {
                    field.setAccessible(true);
                    String className = field.get(instance).getClass().getAnnotation(ClassAnnotation.class).value();

                    xmlList.add("    <" + className + ">");
                    xmlList.add("    </" + className + ">");
                    reflection(field.get(instance), instanceAnnotation.iterable());
                }

                /** Если поле содержит данные, которые нужно записать в файл .xml, добавляем соответствующую строку
                 * в список строк для будущего файла */
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                if (fieldAnnotation != null) {
                    field.setAccessible(true);
                    xmlList.add(xmlList.size() - 1, "        <" + fieldAnnotation.value() + ">"
                            + field.get(instance) + "</" + fieldAnnotation.value() + ">");
                }
            }
        }

        /** Если в метод передали объект, содержащий в себе другие объекты */
        if (iterable) {
            for (Object elem : (Iterable) instance) {
                /** Для каждого объекта добавляем тег-потомок и обрабатываем данный объект с помощью
                 *  {@link Converter#reflection(Object)} */
                String className = elem.getClass().getAnnotation(ClassAnnotation.class).value();
                xmlList.add("    <" + className + ">");
                xmlList.add("    </" + className + ">");
                reflection(elem, false);
            }
        }
    }

    public void addRootTag(){
        xmlList.add(0, "<" + this.rootTag + ">");
        xmlList.add("</" + this.rootTag + ">");
    }

    public void writeXML(Path file) throws IOException {
        Files.write(file, this.xmlList, StandardCharsets.UTF_8);
    }
}
