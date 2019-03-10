package universityBlockchainData;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

import static universityBlockchainData.handlers.test.defaultMethod;
import static universityBlockchainData.handlers.test.testMethod;

public class Server extends AbstractVerticle {
//    static Vertx vertx = Vertx.vertx();
    HttpServerOptions options;
    HttpServer server;
    Server(Vertx vertx){
        start(vertx);
    }

    void start(Vertx vertx) {
        // Do something
        options = new HttpServerOptions().setLogActivity(true);
        server = vertx.createHttpServer(options);

        Router router = Router.router(vertx);
        router.route(HttpMethod.GET,"/").handler(rtx->defaultMethod(rtx));
        router.route(HttpMethod.GET,"/test").handler(rtx->testMethod(rtx));







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
