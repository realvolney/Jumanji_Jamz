package capstone.converters;



import capstone.dynamodb.models.Chart;
import capstone.dynamodb.models.SetList;
import capstone.enums.Genre;
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
//    private ZoneDateTimeConverter converter = new ZoneDateTimeConverter();

    /**
     * Converts provided chart to ChartModel representation
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
                .withMadeBY(chart.getMadeBy())
                .build();
    }

    /**
     * Convert provided SetList to SetListModel representation
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



//
//    /**
//     * Converts a list of Vendors to a list of VendorModels.
//     *
//     * @param vendors The Vendors to convert to VendorModels
//     * @return The converted list of VendorModels
//     */
//    public List<VendorModel> toVendorModelList(List<Vendor> vendors) {
//        List<VendorModel> vendorModels = new ArrayList<>();
//        vendors.forEach(vendor -> vendorModels.add(toVendorModel(vendor)));
//        return vendorModels;
//    }


//    /**
//     * Converts a provided {@link Event} into a {@link EventModel} representation.
//     *
//     * @param event the event to convert
//     * @return the converted event
//     */
//    public EventModel toEventModel(Event event) {
//        Set<String> tags = null;
//        if (event.getTags() != null) {
//            tags = new HashSet<>(event.getTags());
//        }
//        Set<String> vendorList = null;
//        if (event.getVendorList() != null) {
//            vendorList = new HashSet<>(event.getVendorList());
//        }
//
//        return EventModel.builder()
//                .withId(event.getId())
//                .withName(event.getName())
//                .withDate(converter.convert(event.getDate()))
//                .withIsActive(event.getIsActive())
//                .withLocation(event.getLocation())
//                .withPrice(event.getPrice())
//                .withVendorList(vendorList)
//                .withTags(tags)
//                .build();
//    }
//    /**
//     * Converts a list of Event to a list of EventModels.
//     *
//     * @param events The Event to convert to EventModels
//     * @return The converted list of EventModels
//     */
//    public List<EventModel> toEventModelList(List<Event> events) {
//        List<EventModel> eventModels = new ArrayList<>();
//
//        for (Event event: events) {
//            eventModels.add(toEventModel(event));
//        }
//        return eventModels;
//    }
}
