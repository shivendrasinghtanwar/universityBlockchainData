package universityBlockchainData.handlers;

import io.vertx.ext.web.RoutingContext;

public class test {
    public static void testMethod(RoutingContext rtx){
        System.out.println("Test method Called");
        rtx.response().setStatusCode(200).end("Hell yeah");
    }

    public static void defaultMethod(RoutingContext rtx){
        System.out.println("Default method Called");
        rtx.response().setStatusCode(200).end("Welcome to the api End Points");
    }
}
