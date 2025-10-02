import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class UsuarioOperations {

    private static MongoDBConnection connection = new MongoDBConnection();

    public static void main(String[] args) {
        exec();
    }

    private static void exec() {
        MongoDatabase database = connection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("usuarios");
        collection.deleteMany(new Document());
        collection.insertMany(List.of(
                new Usuario("Alice", 25).toDocument(),
                new Usuario("Bob", 30).toDocument(),
                new Usuario("Charlie", 35).toDocument()
        ));

        collection.find().forEach(System.out::println);

        Document filter = new Document("nome", "Bob");
        Bson update = Updates.set("idade", 32);
        collection.updateOne(filter, update);

        collection.find().forEach(System.out::println);

        filter = new Document("nome", "Charlie");
        collection.deleteOne(filter);

        collection.find().forEach(System.out::println);

        connection.closeConnection();
    }
}
