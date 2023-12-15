package capstone.lambda;

import capstone.activity.requests.GetSetListRequest;
import capstone.activity.results.GetSetListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class GetSetListLambda extends LambdaActivityRunner<GetSetListRequest, GetSetListResult>
    implements RequestHandler<LambdaRequest<GetSetListRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    private final URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetSetListRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromPath(path ->
                GetSetListRequest.builder()
                    .withId(decoder.decode(path.get("id"), StandardCharsets.UTF_8))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideGetSetListActivity().handleRequest(request)
        );
    }
}
