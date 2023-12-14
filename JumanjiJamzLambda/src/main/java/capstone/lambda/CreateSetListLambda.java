package capstone.lambda;

import capstone.activity.requests.CreateSetListRequest;
import capstone.activity.results.CreateSetListResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CreateSetListLambda
    extends LambdaActivityRunner<CreateSetListRequest, CreateSetListResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateSetListRequest>, LambdaResponse> {

    private URLDecoder decoder = new URLDecoder();
    private Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateSetListRequest> input, Context context) {

        return super.runActivity(
                () -> {
                    CreateSetListRequest unauthenticatedRequest = input.fromBody(CreateSetListRequest.class);
                    log.info("name {}", unauthenticatedRequest.getName());
                    log.info("charts {}", unauthenticatedRequest.getCharts());
                    log.info("genres {}", unauthenticatedRequest.getGenres());
                    return input.fromUserClaims(claims ->

                            CreateSetListRequest.builder()
                                    .withName(unauthenticatedRequest.getName())
                                    .withCharts(unauthenticatedRequest.getCharts())
                                    .withGenres(unauthenticatedRequest.getGenres())
                                    .withMadeBy(decoder.decode(claims.get("email"), StandardCharsets.UTF_8))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateSetListActivity().handleRequest(request)
        );
    }
}
