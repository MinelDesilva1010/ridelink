import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import org.bson.Document;

public class TestMongo {
    public static void main(String[] args) {
        String uri = "mongodb+srv://cvideshana_db_user:chrishean@cluster0.c4awtjj.mongodb.net/RideLink?appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            System.out.println("Connecting...");
            Document ping = new Document("ping", 1);
            mongoClient.getDatabase("admin").runCommand(ping);
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
