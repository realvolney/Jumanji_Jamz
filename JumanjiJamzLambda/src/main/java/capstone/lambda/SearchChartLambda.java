package capstone.lambda;

import capstone.activity.requests.SearchChartRequest;
import capstone.activity.results.SearchChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchChartLambda
        extends LambdaActivityRunner<SearchChartRequest, SearchChartResult>
        implements RequestHandler<LambdaRequest<SearchChartRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(LambdaRequest<SearchChartRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromQuery(query ->
                SearchChartRequest.builder()
                    .withCriteria(query.get("q"))
                    .build()),
            (request, serviceComponent) ->
                serviceComponent.provideSearchChartActivity().handleRequest(request)
        );
    }
}
