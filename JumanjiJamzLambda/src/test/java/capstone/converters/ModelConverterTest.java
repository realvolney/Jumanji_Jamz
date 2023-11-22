package capstone.converters;

import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    private ModelConverter modelConverter;
    private Chart chart;

    @BeforeEach
    void setUp() {
        modelConverter = new ModelConverter();
    }

    @Test
    void toChartMode_withAllAttributes_convertsChart() {
        // GIVEN
        chart = new Chart();
        chart.setId(UUID.randomUUID());
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
    void toChartMode_withNullAttributes_convertsChart() {
        // GIVEN
        chart = new Chart();
        chart.setId(UUID.randomUUID());
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
}

