public interface IDateTime extends Comparable<IDateTime> {
    int getYear();
    int getMonth();
    int getDay();
    int getHour();
    int getMinute();
    String format();
}