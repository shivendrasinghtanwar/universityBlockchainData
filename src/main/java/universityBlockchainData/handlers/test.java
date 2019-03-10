package universityBlockchainData.handlers;

import io.vertx.ext.web.RoutingContext;

public class test {
    public static void testMethod(RoutingContext rtx){
        System.out.println("Test method Called");
        rtx.response().setStatusCode(200).end("Hell yeah");
    }
}
