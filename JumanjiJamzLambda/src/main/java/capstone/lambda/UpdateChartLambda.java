package capstone.lambda;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UpdateChartLambda extends LambdaActivityRunner<UpdateChartRequest, UpdateChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateChartRequest>, LambdaResponse> {

    private final URLDecoder decoder = new URLDecoder();
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
                        .withMadeBy(decoder.decode(claims.get("email"), StandardCharsets.UTF_8))
                        .build());

                },
                ((updateChartRequest, serviceComponent) ->
                        serviceComponent.provideUpdateChartActivity().handleRequest(updateChartRequest))
        );
    }
}
