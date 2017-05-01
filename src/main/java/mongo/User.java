package mongo;

/**
 * Created by flaps_win10 on 29-04-2017.
 */
public class User
{

    private String Id, date, query, user, text;
    private int polarity, twitterId;

    public User(String id, String date, String query, String user, String text, int polarity, int twitterId) {
        Id = id;
        this.date = date;
        this.query = query;
        this.user = user;
        this.text = text;
        this.polarity = polarity;
        this.twitterId = twitterId;
    }

    public String getId() {
        return Id;
    }

    public String getDate() {
        return date;
    }

    public String getQuery() {
        return query;
    }

    public String getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public int getPolarity() {
        return polarity;
    }

    public int getTwitterId() {
        return twitterId;
    }
}
