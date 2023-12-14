package capstone.lambda;

import capstone.lambda.UpdateChartLambda;
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
        return super.runActivity(
            () -> {
                UpdateChartRequest unauthenticatedRequest = input.fromBody(UpdateChartRequest.class);
//                log.info("id {}", unauthenticatedRequest.getId());
//                log.info("name {}", unauthenticatedRequest.getName());
//                log.info("artist {}", unauthenticatedRequest.getArtist());
//                log.info("BPM {}", unauthenticatedRequest.getBpm());
//                log.info("content {}", unauthenticatedRequest.getContent());
//                log.info("genres {}", unauthenticatedRequest.getGenres());
//                log.info("madeBy {}", unauthenticatedRequest.getMadeBy());

//                log.info("Raw JSON payload: {}", input.getBody());
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
