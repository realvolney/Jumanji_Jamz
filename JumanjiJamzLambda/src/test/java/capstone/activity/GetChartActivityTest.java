package capstone.activity;

import capstone.activity.requests.GetChartRequest;
import capstone.activity.results.GetChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;
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
                .withId(id)
                .build();

        Chart expectedResult = new Chart();
                expectedResult.setId(UUID.fromString(id));
                expectedResult.setName("name");
                expectedResult.setArtist("artist");
                expectedResult.setBpm(123);
                expectedResult.setContent("content");
                expectedResult.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
                expectedResult.setMadeBy("me");

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

        verify(dao, times(1)).getChart(id);
    }

}
