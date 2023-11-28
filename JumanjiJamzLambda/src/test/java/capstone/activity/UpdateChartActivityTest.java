package capstone.activity;

import capstone.activity.requests.UpdateChartRequest;
import capstone.activity.results.UpdateChartResult;
import capstone.dynamodb.ChartDAO;
import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class UpdateChartActivityTest {

    @InjectMocks
    private UpdateChartActivity activity;

    @Mock
    private ChartDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_returnsUpdate() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());
        Chart expectedResult = new Chart();
        expectedResult.setId(UUID.fromString(id));
        expectedResult.setName("name");
        expectedResult.setArtist("artist");
        expectedResult.setBpm(123);
        expectedResult.setContent("content");
        expectedResult.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        expectedResult.setMadeBy("me");

        UpdateChartRequest request = UpdateChartRequest.builder()
                .withId(id)
                .withName("name")
                .withArtist("artist")
                .withBpm(123)
                .withContent("content")
                .withGenres(new HashSet<>(Arrays.asList("Funk", "Soul")))
                .withMadeBy("me")
                .build();

        when(dao.getChart(id)).thenReturn(expectedResult);
        when(dao.saveChart(any(Chart.class))).thenReturn(expectedResult);

        // WHEN
        ChartModel result = activity.handleRequest(request).getChart();

        // THEN
        assertEquals(result.getId(), UUID.fromString(id), "Ids should be equal");
        assertEquals(result.getName(), expectedResult.getName(), "names should be equal");
        assertEquals(result.getArtist(), expectedResult.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), expectedResult.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), expectedResult.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), expectedResult.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), expectedResult.getMadeBy(), "MadeBY should be equal");

        verify(dao, times(1)).getChart(id);
        verify(dao, times(1)).saveChart(expectedResult);
        verify(publisher, times(1)).addCount(
                MetricsConstants.UPDATE_CHART_SUCCESS_COUNT, 1);

    }

}
