package capstone.lambda;

import capstone.activity.requests.GetAllChartsRequest;
import capstone.activity.results.GetAllChartsResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllChartsLambda extends LambdaActivityRunner<GetAllChartsRequest, GetAllChartsResult>
    implements RequestHandler<LambdaRequest<GetAllChartsRequest>, LambdaResponse> {


    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllChartsRequest> input, Context context) {
        return super.runActivity( () -> input.fromQuery(query ->
                GetAllChartsRequest.builder()
                        .withId(query.get("id"))
                        .build()),
                (request, serviceComponent) -> serviceComponent.provideGetAllChartsActivity().handleRequest(request));
    }
}
