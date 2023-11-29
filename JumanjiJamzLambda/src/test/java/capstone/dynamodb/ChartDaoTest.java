package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import capstone.models.ChartModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.accessibility.AccessibleText;

public class ChartDaoTest {
    @InjectMocks
    private ChartDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @Mock
    private DynamoDBMapper mapper;

    private String CREATE_SUCCESS = MetricsConstants.CREATE_CHART_SUCCESS_COUNT;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createChart_ChartSaved_returnsTrue() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Chart chart = new Chart();
        chart.setId(id);
        doNothing().when(mapper).save(chart);
        doNothing().when(publisher).addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 1);

        // WHEN
        boolean result = dao.createChart(chart);

        // THEN
        assertTrue(result, "Should have successfully saved chart");
        verify(mapper, times(1)).save(chart);
        verify(publisher, times(1)).addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 1);
    }

    @Test
    void createChart_saveUnsuccessful_returnsFalse() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Chart chart = new Chart();
        chart.setId(id);
        doThrow(RuntimeException.class).when(mapper).save(chart);
        doNothing().when(publisher).addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 0);

        // WHEN
        boolean result = dao.createChart(chart);

        // THEN
        assertFalse(result, "Should have failed to save chart");
        verify(mapper, times(1)).save(chart);
        verify(publisher, times(1)).addCount(MetricsConstants.CREATE_CHART_SUCCESS_COUNT, 0);
    }

    @Test
    void getChart_chartExists_returnsChart() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        Chart chart = new Chart();
                chart.setId(UUID.fromString(id));
                chart.setName("name");
                chart.setArtist("artist");
                chart.setBpm(123);
                chart.setContent("content");
                chart.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
                chart.setMadeBy("me");

        when(mapper.load(Chart.class, id)).thenReturn(chart);

        // WHEN
        Chart result = dao.getChart(id);

        // THEN
        assertEquals(result.getId(), UUID.fromString(id), "Ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), chart.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).load(Chart.class, id);
        verify(publisher, (times(1))).addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 1);
    }

    @Test
    void getChart_chartDoesNotExists_returnsNull() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        when(mapper.load(Chart.class, id)).thenReturn(null);

        // WHEN
        Chart result = dao.getChart(id);

        // THEN
        assertNull(result, "should return null");
        verify(mapper, times(1)).load(Chart.class, id);
        verify(publisher, (times(1))).addCount(MetricsConstants.GET_CHART_SUCCESS_COUNT, 0);
    }

    @Test
    void saveChart_validSave_returnsChart() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        Chart chart = new Chart();
        chart.setId(UUID.fromString(id));
        chart.setName("name");
        chart.setArtist("artist");
        chart.setBpm(123);
        chart.setContent("content");
        chart.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        chart.setMadeBy("me");
        doNothing().when(mapper).save(chart);

        // WHEN
        Chart result = dao.saveChart(chart);

        // THEN
        assertEquals(result.getId(), UUID.fromString(id), "Ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), chart.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).save(chart);
        verify(publisher, (times(1))).addCount(MetricsConstants.SAVE_CHART_SUCCESS_COUNT, 1);
    }

    @Test
    void saveChart_errorSaving_returnsChart() {
        // GIVEN
        String id = String.valueOf(UUID.randomUUID());

        Chart chart = new Chart();
        chart.setId(UUID.fromString(id));
        chart.setName("name");
        chart.setArtist("artist");
        chart.setBpm(123);
        chart.setContent("content");
        chart.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        chart.setMadeBy("me");

        doThrow(RuntimeException.class).when(mapper).save(chart);

        // WHEN
        Chart result = dao.saveChart(chart);

        // THEN
        assertEquals(result.getId(), UUID.fromString(id), "Ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), chart.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).save(chart);
        verify(publisher, (times(1))).addCount(MetricsConstants.SAVE_CHART_SUCCESS_COUNT, 0);
    }
}
