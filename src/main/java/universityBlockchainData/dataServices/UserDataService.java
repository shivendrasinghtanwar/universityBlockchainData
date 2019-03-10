package universityBlockchainData.dataServices;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

public class UserDataService extends AbstractVerticle {
    MongoClient client;
    public UserDataService(Vertx vertx){
        start(vertx);

    }

    void start(Vertx vertx){
        JsonObject config = new JsonObject().put("db_name","universityBlockchain");
        client = MongoClient.createShared(vertx, config);

        /*JsonObject document = new JsonObject()
                .put("title", "The Hobbit");
        client.save("books", document, res -> {
            if (res.succeeded()) {
                String id = res.result();
                System.out.println("Saved book with id " + id);
            } else {
                res.cause().printStackTrace();
            }
        });*/
    }
}
