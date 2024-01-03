package capstone.lambda;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddChartLambda
        extends LambdaActivityRunner<AddChartRequest, AddChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddChartRequest>, LambdaResponse> {
        private Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddChartRequest> input, Context context) {
        return super.runActivity(
            () -> {
                AddChartRequest unauthenticatedRequest = input.fromBody(AddChartRequest.class);
                log.info("unauthenticated {}", unauthenticatedRequest);
                AddChartRequest userRequest = input.fromUserClaims(claims -> AddChartRequest.builder()
                    .withUser(claims.get("email"))
                    .build()
                );
                log.info("userRequest {}", userRequest);
                return input.fromPath(path ->
                    AddChartRequest.builder()
                        .withId(unauthenticatedRequest.getId())
                        .withName(unauthenticatedRequest.getName())
                        .withArtist(unauthenticatedRequest.getArtist())
                        .withBpm(unauthenticatedRequest.getBpm())
                        .withContent(unauthenticatedRequest.getContent())
                        .withGenres(unauthenticatedRequest.getGenres())
                        .withMadeBy(unauthenticatedRequest.getMadeBy())
                        .withUser(userRequest.getUser())
                        .withSetListId(path.get("id"))
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideAddChartActivity().handleRequest(request)
        );
    }
}
