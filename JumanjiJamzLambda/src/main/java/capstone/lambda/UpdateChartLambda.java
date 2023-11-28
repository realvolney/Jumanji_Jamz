package capstone.lambda;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateChartLambda extends LambdaActivityRunner<UpdateChartRequest, UpdateChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateChartRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateChartRequest> input, Context context) {
        return super.runActivity(
            () -> {
                UpdateChartRequest unauthenticatedRequest = input.fromBody(UpdateChartRequest.class);
                return input.fromUserClaims(claims ->
                    UpdateChartRequest.builder()
                        .withId(unauthenticatedRequest.getId())
                        .withName(unauthenticatedRequest.getName())
                        .withArtist(unauthenticatedRequest.getArtist())
                        .withBpm(unauthenticatedRequest.getBpm())
                        .withContent(unauthenticatedRequest.getContent())
                        .withGenres(unauthenticatedRequest.getGenres())
                        .withMadeBy(claims.get("email"))
                        .build());

                },
                ((updateChartRequest, serviceComponent) ->
                        serviceComponent.provideUpdateChartActivity().handleRequest(updateChartRequest))
        );
    }
}
