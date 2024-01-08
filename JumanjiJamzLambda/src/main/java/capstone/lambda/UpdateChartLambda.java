package capstone.lambda;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UpdateChartLambda extends LambdaActivityRunner<UpdateChartRequest, UpdateChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateChartRequest>, LambdaResponse> {

    private final URLDecoder decoder = new URLDecoder();
    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateChartRequest> input, Context context) {
        log.info("Recieved input {}", input);
        return super.runActivity(
            () -> {
                UpdateChartRequest unauthenticatedRequest = input.fromBody(UpdateChartRequest.class);
                log.info("unauthenticateRequest {}", unauthenticatedRequest);

                UpdateChartRequest claimsRequest = input.fromUserClaims(claims -> UpdateChartRequest.builder()
                        .withMadeBy(claims.get("email"))
                        .build());
                log.info("claimsRequest {}", claimsRequest);
                return input.fromPath(path ->
                    UpdateChartRequest.builder()
                        .withId(decoder.decode(path.get("id"), StandardCharsets.UTF_8))
                        .withName(unauthenticatedRequest.getName())
                        .withArtist(unauthenticatedRequest.getArtist())
                        .withBpm(unauthenticatedRequest.getBpm())
                        .withContent(unauthenticatedRequest.getContent())
                        .withGenres(unauthenticatedRequest.getGenres())
                        .withMadeBy(claimsRequest.getMadeBy())
                        .build());

            },
            (updateChartRequest, serviceComponent) ->
                serviceComponent.provideUpdateChartActivity().handleRequest(updateChartRequest)
        );
    }
}
