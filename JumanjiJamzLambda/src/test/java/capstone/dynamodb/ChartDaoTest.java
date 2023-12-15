package capstone.dynamodb;

import capstone.dynamodb.models.Chart;
import capstone.helper.ChartTestHelper;
import capstone.metrics.MetricsConstants;
import capstone.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.accessibility.AccessibleText;

public class ChartDaoTest {
    @InjectMocks
    private ChartDAO dao;

    @Mock
    private MetricsPublisher publisher;

    @Mock
    private DynamoDBMapper mapper;

    private static final int PAGINIATION_LIMIT = 4;


    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void createChart_ChartSaved_returnsTrue() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Chart chart = new Chart();
        chart.setId(String.valueOf(id));
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
        chart.setId(String.valueOf(id));
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
                chart.setId(String.valueOf(UUID.fromString(id)));
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
        assertEquals(result.getId(), id, "Ids should be equal");
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
        chart.setId(String.valueOf(UUID.fromString(id)));
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
        assertEquals(result.getId(), id, "Ids should be equal");
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
        chart.setId(String.valueOf(UUID.fromString(id)));
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
        assertEquals(result.getId(), id, "Ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "Artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "BPM should be equal");
        assertEquals(result.getContent(), chart.getContent(), "Content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "Genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "MadeBY should be equal");

        verify(mapper, times(1)).save(chart);
        verify(publisher, (times(1))).addCount(MetricsConstants.SAVE_CHART_SUCCESS_COUNT, 0);
    }

    @Test
    void getAllCharts_validId_returnsList() {
        // GIVEN
        String id = "id";
        List<Chart> expectedResult = ChartTestHelper.generateChartList(PAGINIATION_LIMIT);
        ArgumentCaptor<DynamoDBScanExpression> captor = ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        ScanResultPage<Chart> resultPage = new ScanResultPage<>();
        resultPage.setResults(expectedResult);
        when(mapper.scanPage(eq(Chart.class), captor.capture())).thenReturn(resultPage);

        // WHEN
        List<Chart> result = dao.getAllCharts(id, PAGINIATION_LIMIT );
        System.out.println(captor);
        // THEN
        assertEquals(result, resultPage.getResults(), "Lists should contain same elements");

        verify(mapper, times(1)).scanPage(eq(Chart.class), captor.capture());
        int limit = captor.getValue().getLimit();

        assertTrue(limit >= result.size());
    }

    @Test
    void getAllCharts_tableContainsNoItems_returnsList() {
        // GIVEN
        String id = "invalid";
        List<Chart> expectedResult = ChartTestHelper.generateChartList(PAGINIATION_LIMIT);
        ArgumentCaptor<DynamoDBScanExpression> captor = ArgumentCaptor.forClass(DynamoDBScanExpression.class);

        when(mapper.scanPage(eq(Chart.class), captor.capture())).thenReturn(null);

        // WHEN
        List<Chart> result = dao.getAllCharts(id, PAGINIATION_LIMIT);
        System.out.println(captor);
        // THEN
        assertNull(result, "result should be null");

        verify(mapper, times(1)).scanPage(eq(Chart.class), captor.capture());

    }

    @Test
    void searchCharts_validArray_returnsList() {
        // GIVEN
        String[] criteria = {"hello", "governor"};
        Chart chart1 = new Chart();
        chart1.setName("hello");
        Chart chart2 = new Chart();

        chart2.setName("governor");
        ArgumentCaptor<DynamoDBScanExpression> captor = ArgumentCaptor.forClass(DynamoDBScanExpression.class);
        PaginatedScanList<Chart> expectedCharts = mock(PaginatedScanList.class);
        expectedCharts.add(chart1);
        expectedCharts.add(chart2);

        when(mapper.scan(eq(Chart.class), captor.capture())).thenReturn(expectedCharts);

        //WHEN
        List<Chart> result = dao.searchCharts(criteria);
        //THEN
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expectedCharts.get(i).getName(), result.get(i).getName(), "Names should be equal");
        }
        verify(mapper, times(1)).scan(eq(Chart.class), captor.capture());
    }

}
