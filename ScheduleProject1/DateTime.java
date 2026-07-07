public class DateTime implements IDateTime {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    public DateTime(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() { return year; }
    public int getMonth() { return month; }
    public int getDay() { return day; }
    public int getHour() { return hour; }
    public int getMinute() { return minute; }

    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    public int compareTo(IDateTime o) {
        if (this.year != o.getYear()) return Integer.compare(this.year, o.getYear());
        if (this.month != o.getMonth()) return Integer.compare(this.month, o.getMonth());
        if (this.day != o.getDay()) return Integer.compare(this.day, o.getDay());
        if (this.hour != o.getHour()) return Integer.compare(this.hour, o.getHour());
        return Integer.compare(this.minute, o.getMinute());
    }
}