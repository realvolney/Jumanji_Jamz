package capstone.activity;

import capstone.activity.requests.GetChartRequest;
import capstone.activity.results.GetChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.models.ChartModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

public class GetChartActivityTest {
    @InjectMocks
    private GetChartActivity activity;

    @Mock
    private ChartDAO dao;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_getsRequest_returnsResult() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());
        GetChartRequest request = GetChartRequest.builder()
                .withId(String.valueOf(id))
                .build();

        ChartModel expectedResult = ChartModel.builder()
                .withId(UUID.fromString(id))
                .withName("name")
                .withArtist("artist")
                .withBpm(123)
                .withContent("content")
                .withGenres(new HashSet<>(Arrays.asList("Funk", "Soul")))
                .withMadeBY("me")
                .build();
        when(dao.getChart(id)).thenReturn(expectedResult);

        // WHEN
        GetChartResult result = activity.handleRequest(request);

        // THEN
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), expectedResult.getName(), "names should be equal");
        assertEquals(result.getArtist(), expectedResult.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), expectedResult.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), expectedResult.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), expectedResult.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), expectedResult.getMadeBy(), "MadeBY should be equal");
    }

}
