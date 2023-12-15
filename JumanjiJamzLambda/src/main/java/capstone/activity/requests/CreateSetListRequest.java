package capstone.activity.requests;

import capstone.dynamodb.models.Chart;
import capstone.models.ChartModel;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

import static capstone.utils.CollectionUtils.copyToSet;

@JsonDeserialize(builder = CreateSetListRequest.Builder.class)
public class CreateSetListRequest {
    private final String name;
    private final Set<ChartModel> charts;
    private final Set<String> genres;
    private final String madeBy;
    private Logger log = LogManager.getLogger();

    private CreateSetListRequest(String name, Set<ChartModel> charts, Set<String> genres, String madeBy) {

        this.name = name;
        log.info("name {}", name);
        this.charts = charts;
        log.info("charts {}", charts);
        this.genres = genres;
        log.info("genres {}", genres);
        this.madeBy = madeBy;
        log.info("name {}", genres);


    }

    public String getName() {
        return name;
    }

    public Set<ChartModel> getCharts() {
        return charts;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public String getMadeBy() {
        return madeBy;
    }

    @Override
    public String toString() {
        return "CreateChartRequest{" +
                "name= " + name + '\'' +
                ", charts= " + charts + '\'' +
                ", genres= " + genres + '\'' +
                ", madeBy= " + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private Set<ChartModel> charts;
        private Set<String> genres;
        private String madeBy;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCharts(Set<ChartModel> charts) {
            this.charts = copyToSet(charts);
            return this;
        }

        public Builder withGenres(Set<String> genres) {
            this.genres = copyToSet(genres);
            return this;
        }

        public Builder withMadeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public CreateSetListRequest build() {
            return new CreateSetListRequest(name, charts, genres, madeBy);
        }
    }
}
