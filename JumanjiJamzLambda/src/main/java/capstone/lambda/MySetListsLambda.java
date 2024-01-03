package capstone.lambda;

import capstone.activity.requests.MySetListsRequest;
import capstone.activity.results.MySetListsResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class MySetListsLambda extends LambdaActivityRunner<MySetListsRequest, MySetListsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<MySetListsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    private final URLDecoder decoder = new URLDecoder();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<MySetListsRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                MySetListsRequest.builder()
                    .withMadeBy(decoder.decode(claims.get("email"), StandardCharsets.UTF_8))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideMySetListsActivity().handleRequest(request)
        );
    }
}
