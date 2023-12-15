package capstone.converters;

import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.helper.ChartTestHelper;
import capstone.models.ChartModel;
import capstone.models.SetListModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ModelConverterTest {
    private ModelConverter modelConverter;
    private Chart chart;
    private SetList setList;

    @BeforeEach
    void setUp() {
        modelConverter = new ModelConverter();
    }

    @Test
    void toChartModel_withAllAttributes_convertsChart() {
        // GIVEN
        chart = new Chart();
        chart.setId(String.valueOf(UUID.randomUUID()));
        chart.setName("name");
        chart.setArtist("artist");
        chart.setBpm(123);
        chart.setContent("Hello");
        chart.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        chart.setMadeBy("me");

        // WHEN
        ChartModel result = modelConverter.toChartModel(chart);

        // THEN
        assertEquals(result.getId(), chart.getId(), "ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "bpm should be equal");
        assertEquals(result.getContent(), chart.getContent(), "content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "madeBy should be equal");
    }

    @Test
    void toChartModel_withNullAttributes_convertsChart() {
        // GIVEN
        chart = new Chart();
        chart.setId(String.valueOf(UUID.randomUUID()));
        chart.setName(null);
        chart.setArtist(null);
        chart.setBpm(null);
        chart.setContent(null);
        chart.setGenres(null);
        chart.setMadeBy(null);

        // WHEN
        ChartModel result = modelConverter.toChartModel(chart);

        // THEN
        assertEquals(result.getId(), chart.getId(), "ids should be equal");
        assertEquals(result.getName(), chart.getName(), "names should be equal");
        assertEquals(result.getArtist(), chart.getArtist(), "artists should be equal");
        assertEquals(result.getBpm(), chart.getBpm(), "bpm should be equal");
        assertEquals(result.getContent(), chart.getContent(), "content should be equal");
        assertEquals(result.getGenres(), chart.getGenres(), "genres should be equal");
        assertEquals(result.getMadeBy(), chart.getMadeBy(), "madeBy should be equal");
    }

    @Test
    void toSetListModel_withAttributes_ConvertsSetList() {
        // GIVEN
        setList = new SetList();
        setList.setId(String.valueOf(UUID.randomUUID()));
        setList.setName("name");
        setList.setCharts(new HashSet<>(ChartTestHelper.generateChartModelList(4)));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul")));
        setList.setMadeBy("me");

        // WHEN
        SetListModel result = modelConverter.toSetListModel(setList);

        // THEN
        assertEquals(result.getId(), setList.getId(), "ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertEquals(result.getCharts(), setList.getCharts(), "charts should be the same");
        assertEquals(result.getGenres(), setList.getGenres(), "genres should equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "madeby should be same person");
    }

    @Test
    void toSetListModel_withNullAttributes_ConvertsSetList() {
        // GIVEN
        setList = new SetList();
        setList.setId(String.valueOf(UUID.randomUUID()));
        setList.setName(null);
        setList.setCharts(null);
        setList.setGenres(null);
        setList.setMadeBy("me");

        // WHEN
        SetListModel result = modelConverter.toSetListModel(setList);

        // THEN
        assertEquals(result.getId(), setList.getId(), "ids should be equal");
        assertEquals(result.getName(), setList.getName(), "names should be equal");
        assertNull(result.getCharts(), "charts should be null");
        assertEquals(result.getGenres(), setList.getGenres(), "genres should equal");
        assertEquals(result.getMadeBy(), setList.getMadeBy(), "madeby should be same person");
    }

    @Test
    void toChartModelList_givenListCharts_returnsConvertedList() {
        // GIVEN
        List<Chart> chartList = ChartTestHelper.generateChartList(10);

        // WHEN
        List<ChartModel> result = modelConverter.toChartModelList(chartList);

        // THEN
        for (int i = 0; i < chartList.size(); i++) {
            ChartModel model = result.get(i);
            Chart chart = chartList.get(i);
            assertEquals(chart.getId(), model.getId(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getId(), model.getId()));
            assertEquals(chart.getName(), model.getName(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getName(), model.getName()));
            assertEquals(chart.getArtist(), model.getArtist(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getArtist(), model.getArtist()));
            assertEquals(chart.getBpm(), model.getBpm(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getBpm(), model.getBpm()));
            assertEquals(chart.getContent(), model.getContent(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getContent(), model.getContent()));
            assertEquals(chart.getGenres(), model.getGenres(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getGenres(), model.getGenres()));
            assertEquals(chart.getMadeBy(), model.getMadeBy(),
                    String.format("chart: '%s' and model: '%s' should be equal", chart.getMadeBy(), model.getMadeBy()));
        }

    }
}

