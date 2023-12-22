package capstone.helper;


import capstone.converters.ModelConverter;
import capstone.dynamodb.models.SetList;
import capstone.models.SetListModel;

import java.util.*;

public class SetListTestHelper {
    private static final ModelConverter converter = new ModelConverter();
    private SetListTestHelper() {
    }

    public static List<SetList> generateSetListList(int size) {
        List<SetList> setList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            setList.add(generateSetList(i));
        }
        return setList;
    }

    public static List<SetListModel> generateSetListModelList(int size) {
        List<SetListModel> setlist = new ArrayList<>();


        for (int i = 0; i < size; i++) {
            setlist.add(converter.toSetListModel(generateSetList(i)));
        }
        return setlist;
    }
    public static SetList generateSetList(int sequence) {
        SetList setList = new SetList();
        setList.setId(String.valueOf(UUID.randomUUID()));
        setList.setName("name" + sequence);
        setList.setMadeBy("madeBy" + sequence);
        setList.setCharts(new HashSet<>(Arrays.asList(converter.toChartModel(
                ChartTestHelper.generateChart(sequence)))));
        setList.setGenres(new HashSet<>(Arrays.asList("Funk", "Soul", String.valueOf(sequence))));


        return setList;
    }
}
