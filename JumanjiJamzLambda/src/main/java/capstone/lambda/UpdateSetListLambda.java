package capstone.lambda;

import capstone.activity.requests.UpdateSetListRequest;
import capstone.activity.results.UpdateSetListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateSetListLambda extends LambdaActivityRunner<UpdateSetListRequest, UpdateSetListResult>
    implements RequestHandler<AuthenticatedLambdaRequest<UpdateSetListRequest>, LambdaResponse> {


    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateSetListRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    UpdateSetListRequest unauthenticatedRequest = input.fromBody(UpdateSetListRequest.class);
                    return input.fromUserClaims(claims ->
                            UpdateSetListRequest.builder()
                                    .withId(unauthenticatedRequest.getId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withCharts(unauthenticatedRequest.getCharts())
                                    .withGenres(unauthenticatedRequest.getGenres())
                                    .withMadeBy(claims.get("email"))
                                    .build());

                },
                ((request, serviceComponent) ->
                        serviceComponent.provideUpdateSetListActivity().handleRequest(request))
        );
    }
}
