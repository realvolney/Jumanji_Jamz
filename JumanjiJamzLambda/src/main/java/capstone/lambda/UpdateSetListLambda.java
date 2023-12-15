package capstone.lambda;

import capstone.activity.requests.UpdateSetListRequest;
import capstone.activity.results.UpdateSetListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UpdateSetListLambda extends LambdaActivityRunner<UpdateSetListRequest, UpdateSetListResult>
    implements RequestHandler<AuthenticatedLambdaRequest<UpdateSetListRequest>, LambdaResponse> {

    private Logger log = LogManager.getLogger();
    private final URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateSetListRequest> input, Context context) {
        return super.runActivity(
            () -> {
                UpdateSetListRequest unauthenticatedRequest = input.fromBody(UpdateSetListRequest.class);
                UpdateSetListRequest claimsRequest = input.fromUserClaims(claims -> UpdateSetListRequest.builder()
                    .withMadeBy(decoder.decode(claims.get("email"), StandardCharsets.UTF_8))
                    .build());
                return input.fromPath(path ->
                    UpdateSetListRequest.builder()
                        .withId(decoder.decode(path.get("id"), StandardCharsets.UTF_8))
                        .withName(unauthenticatedRequest.getName())
                        .withCharts(unauthenticatedRequest.getCharts())
                        .withGenres(unauthenticatedRequest.getGenres())
                        .withMadeBy(claimsRequest.getMadeBy())
                        .build());

            },
            (request, serviceComponent) ->
                serviceComponent.provideUpdateSetListActivity().handleRequest(request)
        );
    }
}
