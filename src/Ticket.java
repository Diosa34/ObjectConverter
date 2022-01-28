@ClassAnnotation("element")
public class Ticket implements Convertible{
    @FieldAnnotation("number")
    private int number;
    @FieldAnnotation("title")
    private String title;
    @FieldAnnotation("date")
    private String date;
    @FieldAnnotation("time")
    private String time;
    @FieldAnnotation("price")
    private String price;
    @FieldAnnotation("where")
    private String where;
    @FieldAnnotation("row")
    private int row;
    @FieldAnnotation("place")
    private int place;


    public Ticket(int number, String title, String date, String time, String price, String where, int row, int place) {
        this.number = number;
        this.title = title;
        this.date = date;
        this.time = time;
        this.price = price;
        this.where = where;
        this.row = row;
        this.place = place;
    }
}
