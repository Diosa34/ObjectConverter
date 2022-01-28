import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;


public class Converter {
    FileWriter writer;

    Converter(String fileName) throws IOException {
        /** Creating a file to write to */
        this.writer = new FileWriter(fileName);
    }

    private void writeToFile(Convertible instance, Integer indent) throws IllegalAccessException, IOException {
        /** If an object that does not contain other objects is passed to the method */
        String className = instance.getClass().getAnnotation(ClassAnnotation.class).value();
        writer.write(" ".repeat(indent)+"<" + className + ">\n");


        if (!Iterable.class.isAssignableFrom(instance.getClass())) {
            for (Field field : instance.getClass().getDeclaredFields()) {
                /** If the field contains data that needs to be written to the .xml file, write it to the file */
                FieldAnnotation fieldAnnotation = field.getAnnotation(FieldAnnotation.class);
                if (fieldAnnotation != null) {
                    field.setAccessible(true);
                    writer.write("        <" + fieldAnnotation.value() + ">"
                            + field.get(instance) + "</" + fieldAnnotation.value() + ">\n");
                }
            }
        }

        /** If an object that contain other objects is passed to the method */
        else {
            for (Object elem : (Iterable) instance) {
                /** For each object, we add a child-tag and call {@link Converter#writeToFile(Convertible, Integer)} */
                if (Convertible.class.isAssignableFrom(elem.getClass())){
                    writeToFile((Convertible) elem, indent + 4);
                }
                else {
                    throw new IllegalArgumentException("The objects contained in an instance do not implements interface 'Convertible'");
                }
            }
        }
        writer.write(" ".repeat(indent)+"</" + className + ">\n");
    }

    /** Call {@link Converter#writeToFile(Convertible, Integer)} to convert an object, write data to the file
     *  and close file */
    public void xmlInitialization(Convertible instance, Integer indent) throws IOException, IllegalAccessException {
        writeToFile(instance, indent);
        writer.flush();
        writer.close();
    }
}
