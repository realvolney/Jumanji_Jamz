package capstone.activity.requests;

import java.util.Set;

public class CreateSetListRequest {
    private final String name;
    private final Set<String> charts;
    private final Set<String> genres;
    private final String madeBy;

    private CreateSetListRequest(String name, Set<String> charts, Set<String> genres, String madeBy) {
        this.name = name;
        this.charts = charts;
        this.genres = genres;
        this.madeBy = madeBy;
    }

    public String getName() {
        return name;
    }

    public Set<String> getCharts() {
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

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String name;
        private Set<String> charts;
        private Set<String> genres;
        private String madeBy;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCharts(Set<String> charts) {
            this.charts = charts;
            return this;
        }

        public Builder withGenres(Set<String> genres) {
            this.genres = genres;
            return this;
        }

        public Builder withMadeBy(String name) {
            this.madeBy = madeBy;
            return this;
        }

        public CreateSetListRequest build() {
            return new CreateSetListRequest(name, charts, genres, madeBy);
        }
    }
}
