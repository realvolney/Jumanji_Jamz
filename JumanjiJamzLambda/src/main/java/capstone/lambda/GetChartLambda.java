package capstone.lambda;

import capstone.activity.requests.GetChartRequest;
import capstone.activity.results.GetChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class GetChartLambda extends LambdaActivityRunner<GetChartRequest, GetChartResult>
        implements RequestHandler<LambdaRequest<GetChartRequest>, LambdaResponse> {
    private final Logger log = LogManager.getLogger();
    private final URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetChartRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromPath(path ->
                GetChartRequest.builder()
                    .withId(decoder.decode(path.get("id"), StandardCharsets.UTF_8))
                    .build()),
            (getChartRequest, serviceComponent) ->
                serviceComponent.provideGetChartActivity().handleRequest(getChartRequest)
        );
    }
}
