package capstone.lambda;

import capstone.activity.requests.DeleteSetListRequest;
import capstone.activity.results.DeleteSetListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class DeleteSetListLambda extends LambdaActivityRunner<DeleteSetListRequest, DeleteSetListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteSetListRequest>, LambdaResponse> {
    private Logger log = LogManager.getLogger();
    private URLDecoder decoder = new URLDecoder();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteSetListRequest> input, Context context) {
        log.info("handleRequest");
        DeleteSetListRequest unauthenticatedRequest = input.fromPath(path -> {
            return DeleteSetListRequest.builder()
                   .withId(decoder.decode(path.get("id"), StandardCharsets.UTF_8)).build();
        });
        return super.runActivity(

                () -> input.fromUserClaims(claims ->
                        DeleteSetListRequest.builder()
                                .withId(unauthenticatedRequest.getId())
                                .withMadBy(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteSetListActivity().handleRequest(request)
        );
    }
}
