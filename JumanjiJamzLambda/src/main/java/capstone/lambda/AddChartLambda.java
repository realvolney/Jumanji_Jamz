package capstone.lambda;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddChartLambda
        extends LambdaActivityRunner<AddChartRequest, AddChartResult>
        implements RequestHandler<AuthenticatedLambdaRequest<AddChartRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<AddChartRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    AddChartRequest unauthenticatedRequest = input.fromBody(AddChartRequest.class);
                    AddChartRequest userRequest = input.fromUserClaims(claims -> AddChartRequest.builder()
                                .withUser(claims.get("email"))
                                .build()
                    );
                    return input.fromQuery(query ->
                            AddChartRequest.builder()
                                    .withId(unauthenticatedRequest.getId())
                                    .withName(unauthenticatedRequest.getName())
                                    .withArtist(unauthenticatedRequest.getArtist())
                                    .withBpm(unauthenticatedRequest.getBpm())
                                    .withContent(unauthenticatedRequest.getContent())
                                    .withGenres(unauthenticatedRequest.getGenres())
                                    .withMadeBy(unauthenticatedRequest.getMadeBy())
                                    .withUser(userRequest.getUser())
                                    .withSetListId(query.get("setListId"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideAddChartActivity().handleRequest(request)
        );
    }
}
