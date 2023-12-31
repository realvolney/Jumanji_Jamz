package capstone.converters;

import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;

import capstone.models.ChartModel;
import capstone.models.SetListModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Converts between Data and API models.
 */
public class ModelConverter {


    /**
     * Converts provided chart to ChartModel representation.
     * @param chart the Chart to convert to CHartModel
     * @return the converted ChartModel
     */
    public ChartModel toChartModel(Chart chart) {
        Set<String> genres = null;
        if (chart.getGenres() != null) {
            genres = new HashSet<>(chart.getGenres());
        }

        return ChartModel.builder()
                .withId(chart.getId())
                .withName(chart.getName())
                .withArtist(chart.getArtist())
                .withBpm(chart.getBpm())
                .withContent(chart.getContent())
                .withGenres(genres)
                .withMadeBy(chart.getMadeBy())
                .build();
    }

    /**
     * Convert provided List of charts to a List of ChartModels.
     * @param charts the List of charts to be converted
     * @return the converted chartModels
     */
    public List<ChartModel> toChartModelList(List<Chart> charts) {
        List<ChartModel> modelList = new ArrayList<>();
        charts.forEach(chart -> modelList.add(toChartModel(chart)));
        return modelList;
    }


    /**
     * Convert provided SetList to SetListModel representation.
     * @param setList the SetList to be converted
     * @return the converted SetListModel
     */
    public SetListModel toSetListModel(SetList setList) {
        return SetListModel.builder()
               .withId(setList.getId())
               .withName(setList.getName())
               .withCharts(setList.getCharts())
               .withGenres(setList.getGenres())
               .withMadeBy(setList.getMadeBy())
               .build();
    }

    /**
     * Convert provided List of setLists to a List of SetListModels.
     * @param setLists the List of charts to be converted
     * @return the converted seListModels
     */
    public List<SetListModel> toSetListModelList(List<SetList> setLists) {
        List<SetListModel> modelList = new ArrayList<>();
        setLists.forEach(setList -> modelList.add(toSetListModel(setList)));
        return modelList;
    }

}
