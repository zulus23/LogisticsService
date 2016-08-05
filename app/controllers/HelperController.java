package controllers;

import play.libs.Json;
import play.mvc.Result;
import services.HelperForInterface;

import javax.inject.Inject;

import static play.mvc.Results.ok;

/**
 * Created by Gukov on 05.08.2016.
 */
public class HelperController {

    @Inject
    private HelperForInterface helperForInterface;

    public  Result listdb() {
        return ok(Json.toJson(helperForInterface.listDB()));
    }

    public Result getTypeReport(){
        return ok(Json.toJson(helperForInterface.typeReportList()));
    }
}
