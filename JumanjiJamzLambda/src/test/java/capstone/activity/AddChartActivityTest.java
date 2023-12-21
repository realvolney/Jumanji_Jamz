package capstone.activity;

import capstone.activity.requests.AddChartRequest;
import capstone.activity.results.AddChartResult;
import capstone.dynamodb.SetListDAO;
import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.metrics.MetricsPublisher;
import capstone.models.ChartModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class AddChartActivityTest {
    @InjectMocks
    private AddChartActivity activity;

    @Mock
    private SetListDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void handleRequest_validRequest_validResponse() {
        // GIVEN
        AddChartRequest request = AddChartRequest.builder()
                .withId("id")
                .withName("name")
                .withArtist("artist")
                .withBpm(123)
                .withContent("content")
                .withGenres(new HashSet<>(Arrays.asList("FUnk", "Soul")))
                .withMadeBy("me")
                .withSetListId("1")
                .build();

        SetList setList = new SetList();
        setList.setId("1");

        when(dao.getSetList("1")).thenReturn(setList);
        when(dao.saveSetList(any(SetList.class))).thenReturn(setList);
        // WHEN
        AddChartResult result = activity.handleRequest(request);
        ChartModel chart = result.getChart();
        // THEN
        assertEquals(chart.getId(), request.getId(), "Ids should be equal");
        assertEquals(chart.getName(), request.getName(), "Names should be equal");
        assertEquals(chart.getArtist(), request.getArtist(), "Artists should be the same");
        assertEquals(chart.getBpm(), request.getBpm(), "Bpms should be equal");
        assertEquals(chart.getContent(), request.getContent(), "Content should be equal");
        assertEquals(chart.getGenres(), request.getGenres(), "Genres should be equal");
        assertEquals(chart.getMadeBy(), request.getMadeBy(), "MadeBy should be equal");

        verify(dao, times(1)).getSetList("1");
        verify(dao, times(1)).saveSetList(any(SetList.class));
    }
}
