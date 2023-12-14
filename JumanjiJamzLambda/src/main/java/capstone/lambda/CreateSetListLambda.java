package capstone.lambda;

import capstone.activity.requests.CreateSetListRequest;
import capstone.activity.results.CreateSetListResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CreateSetListLambda
    extends LambdaActivityRunner<CreateSetListRequest, CreateSetListResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateSetListRequest>, LambdaResponse> {

    private final URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateSetListRequest> input, Context context) {

        return super.runActivity(
                () -> {
                    CreateSetListRequest unauthenticatedRequest = input.fromBody(CreateSetListRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateSetListRequest.builder()
                                    .withName(unauthenticatedRequest.getName())
                                    .withCharts(unauthenticatedRequest.getCharts())
                                    .withGenres(unauthenticatedRequest.getGenres())
                                    .withMadeBy(decoder.decode(claims.get("id"), StandardCharsets.UTF_8))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateSetListActivity().handleRequest(request)
        );
    }
}
