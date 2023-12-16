package capstone.lambda;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddChartLambda
        extends LambdaActivityRunner<AddChartRequest, AddChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddChartRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddChartRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddChartRequest unauthenticatedRequest = input.fromBody(AddChartRequest.class);
                    return input.fromUserClaims(claims ->
                            AddChartRequest.builder()
                                    .withId(unauthenticatedRequest.getId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withArtist(unauthenticatedRequest.getArtist())
                                    .withBpm(unauthenticatedRequest.getBpm())
                                    .withContent(unauthenticatedRequest.getContent())
                                    .withGenres(unauthenticatedRequest.getGenres())
                                    .withMadeBy(claims.get("name"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddChartActivity().handleRequest(request)
        );
    }
}
