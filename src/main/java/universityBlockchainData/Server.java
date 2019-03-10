package universityBlockchainData;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import static universityBlockchainData.handlers.test.testMethod;

public class Server extends AbstractVerticle {
    static Vertx vertx = Vertx.vertx();
    HttpServerOptions options;
    HttpServer server;
    public void start() {
        // Do something
        options = new HttpServerOptions().setLogActivity(true);
        server = vertx.createHttpServer(options);

        Router router = Router.router(vertx);
        router.route("/test").handler(rtx->testMethod(rtx));





        server.requestHandler(router).listen(8080,res->{
            if(res.succeeded()){
                System.out.println("\t**Server started**");
            }
            else{
                System.out.println("Server start Failed!!");
            }
        });

    }

}
