package capstone.activity.results;

import java.util.Set;

public class GetSetListResult {
    private String id;
    private String name;
    private Set<String> charts;
    private Set<String> genres;
    private String madeBy;

    private GetSetListResult(String id, String name, Set<String> charts,
                            Set<String> genres, String madeBy) {
        this.id = id;
        this.name = name;
        this.charts = charts;
        this.genres = genres;
        this.madeBy = madeBy;
    }

    public String getId() {
        return id;
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
        return "GetSetListResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", charts=" + charts +
                ", genres=" + genres +
                ", madeBy='" + madeBy + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String id;
        private String name;
        private Set<String> charts;
        private Set<String> genres;
        private String madeBy;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

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

        public Builder withMadeBy(String madeBy) {
            this.madeBy = madeBy;
            return this;
        }

        public GetSetListResult build() {
            return new GetSetListResult(id, name, charts, genres, madeBy);
        }
    }
}
