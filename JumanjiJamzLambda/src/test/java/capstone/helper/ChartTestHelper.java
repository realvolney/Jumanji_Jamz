package capstone.helper;

import capstone.converters.ModelConverter;
import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;

import java.util.*;

public class ChartTestHelper {
    private ChartTestHelper() {
    }

    public static List<Chart> generateChartList(int size) {
        List<Chart> chartList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            chartList.add(generateChart(i));
        }
        return chartList;
    }

    public static List<ChartModel> generateChartModelList(int size) {
        List<ChartModel> chartList = new ArrayList<>();
        ModelConverter converter = new ModelConverter();

        for (int i = 0; i < size; i++) {
            chartList.add(converter.toChartModel(generateChart(i)));
        }
        return chartList;
    }
    public static Chart generateChart(int sequence) {
        Chart chart = new Chart();
        chart.setId(String.valueOf(UUID.randomUUID()));
        chart.setName("name" + sequence);
        chart.setArtist("artist" + sequence);
        chart.setBpm(sequence);
        chart.setContent("content" + sequence);
        chart.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul", String.valueOf(sequence))));
        chart.setMadeBy("madeBy" + sequence);

        return chart;
    }
}
