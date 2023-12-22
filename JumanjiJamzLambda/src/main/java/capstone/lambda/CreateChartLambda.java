package capstone.lambda;

import capstone.activity.requests.CreateChartRequest;
import capstone.activity.results.CreateChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CreateChartLambda
    extends LambdaActivityRunner<CreateChartRequest, CreateChartResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateChartRequest>, LambdaResponse> {

    private URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateChartRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateChartRequest unauthenticatedRequest = input.fromBody(CreateChartRequest.class);
                return input.fromUserClaims(claims ->
                    CreateChartRequest.builder()
                        .withName(unauthenticatedRequest.getName())
                        .withArtist(unauthenticatedRequest.getArtist())
                        .withContent(unauthenticatedRequest.getContent())
                        .withBpm(unauthenticatedRequest.getBpm())
                        .withGenres(unauthenticatedRequest.getGenres())
                        .withMadeBy(decoder.decode(claims.get("email"), StandardCharsets.UTF_8))
                        .build());
            },
            (request, serviceComponent) ->
                serviceComponent.provideCreateCharActivity().handleRequest(request)

        );
    }
}
