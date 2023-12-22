package capstone.lambda;

import capstone.activity.requests.SearchChartRequest;
import capstone.activity.results.SearchChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class SearchChartLambda
        extends LambdaActivityRunner<SearchChartRequest, SearchChartResult>
        implements RequestHandler<LambdaRequest<SearchChartRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    private final URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchChartRequest> input, Context context) {
        log.info("handleRequest");
        log.info("input {}", input);
        log.info("context {}", context);
        return super.runActivity(

            () -> input.fromQuery(query ->
                SearchChartRequest.builder()
                    .withCriteria(decoder.decode(query.get("q"), StandardCharsets.UTF_8))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideSearchChartActivity().handleRequest(request)
        );
    }
}
