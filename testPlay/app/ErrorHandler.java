import com.fasterxml.jackson.databind.JsonNode;
import play.http.HttpErrorHandler;
import play.mvc.*;
import play.mvc.Http.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.inject.Singleton;
import play.libs.Json;

@Singleton
public class ErrorHandler implements HttpErrorHandler {

    private static class InternalServerError {
        public String realm = "ServerError";
        public String message = null;
        private InternalServerError(String message) {
            this.message = message;
        }
        public static JsonNode apply(String message) {
            return Json.toJson(new InternalServerError(message));
        }
    }

    private static class ClientError {
        public String realm = "ClientError";
        public String message = null;
        private ClientError(String message) {
            this.message = message;
        }
        public static JsonNode apply(String message) {
            return Json.toJson(new ClientError(message));
        }
    }


    public CompletionStage<Result> onClientError(RequestHeader request, int statusCode, String message) {
        return CompletableFuture.completedFuture(
                Results.status(statusCode, ClientError.apply(message))
        );
    }

    public CompletionStage<Result> onServerError(RequestHeader request, Throwable exception) {
        return CompletableFuture.completedFuture(
                Results.internalServerError(InternalServerError.apply(exception.getMessage()))
        );
    }

}
