package spbu.apmath.mytaskmanager;

public class SimpleEvent {
    private String mDate;
    private String mText;

    public SimpleEvent(String date, String text) {
        mDate = date;
        mText = text;
    }

    public String getDate() {
        return mDate;
    }

    public String getText() {
        return mText;
    }
}
