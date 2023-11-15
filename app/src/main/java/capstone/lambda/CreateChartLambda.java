package capstone.lambda;

import capstone.activity.requests.CreateChartRequest;
import capstone.activity.results.CreateChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateChartLambda
    extends LambdaActivityRunner<CreateChartRequest, CreateChartResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateChartRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateChartRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateChartRequest unauthenticatedRequest = input.fromBody(CreateChartRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateChartRequest.builder()
                                    .withName(unauthenticatedRequest.getName()))
                            .build()

                }
        )
    }
}
