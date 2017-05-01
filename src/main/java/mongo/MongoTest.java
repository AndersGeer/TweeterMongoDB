package mongo;

import asg.cliche.Command;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MongoTest {

    public static void main(String[] args) throws IOException {
        MongoTest dbTest = new MongoTest("mongodb://localhost:27017", "social_net", "tweets" );
        Shell shell = ShellFactory.createConsoleShell("Tweeter", "Welcome to tweets-console - ?help for instructions",dbTest);
        shell.commandLoop();

    }

    MongoClientURI connStr;
    MongoClient mongoClient;
    MongoDatabase db;
    MongoCollection<Document> tweets;
    public MongoTest(String uri, String dbName, String collectionName) {
        connStr = new MongoClientURI(uri);
        mongoClient = new MongoClient(connStr);

        db = mongoClient.getDatabase(dbName);
        tweets = db.getCollection(collectionName);



    }

    //Commands
    @Command(description="Get all distinct users")
    public void getUsers ()
    {
        /*
        Currently not working.
        Codec issues.
         */
        DistinctIterable<User> cursor = tweets.distinct("user", User.class);

        System.out.println(cursor.first());
    }

    @Command
    public void tweetsByUser(String user)
    {
        /*
        Not part of the assignment, proof of working connection
         */
        BasicDBObject query = new BasicDBObject();
        query.put("user", user);
        MongoCursor cursor = tweets.find(query).iterator();
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }

    }

    @Command
    public void getMentions()
    {
        AggregateIterable<Document> output = tweets.aggregate(Arrays.asList(
                new Document("$match",  Pattern.compile("/@\\w+/")),
                new Document("$group", new Document("_id","$id").append("text","$text"))
            )
        );
        for (Document doc: output)
        {
            System.out.println(doc);
            System.out.println(doc.get("text"));
        }
        System.out.println(output);
    }

}

//Distinct users: db.tweets.distinct("user")

/*Gets all tweets with a mention
db.tweets.aggregate(
                                   [
                                     {$match:{text:/@\w+/}},
                                     {$group:{_id:"$id",text:{$push:"$text"}}}
                                   ],
                                   {
                                     allowDiskUse: true
                                   }
                                 )
 */



/*Gets all tweets with a link to another twitter user
db.tweets.aggregate(
                                   [
                                     {$match:{text:/http:\/\/twitter.com\/\w+/}},
                                     {$group:{_id:"$user" ,text:{$push:"$text"}}}
                                   ],
                                   {
                                     allowDiskUse: true
                                   }
                                 )
 */
