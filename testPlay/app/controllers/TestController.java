package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import play.libs.Json;

public class TestController extends Controller{

    private static class TTTT {
        public String aa = "11";
        public String bb = "22";

    }
    private static class Counter {
        protected static Integer counter = 0;
        protected static JsonNode apply() {
            return Json.toJson(new Counter());
        }
        private Counter() {
            this.num = counter;
            counter += 1;
        }

        public Integer num = null;
        public String str = "Static";
        public TTTT inner = new TTTT();
    }

    public Result res() {
        return ok(Counter.apply());
    }
}
