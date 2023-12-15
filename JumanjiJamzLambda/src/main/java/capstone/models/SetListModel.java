package capstone.models;

import java.util.Objects;
import java.util.Set;

public class SetListModel {
    private final String id;
    private final String name;
    private final Set<String> charts;
    private final Set<String> genres;
    private final String madeBy;

    private SetListModel(String id, String name, Set<String> charts, Set<String> genres, String madeBy) {
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetListModel that = (SetListModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(charts, that.charts) &&
                Objects.equals(genres, that.genres) && Objects.equals(madeBy, that.madeBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, charts, genres, madeBy);
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }
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

        public SetListModel build() {
            return new SetListModel(id, name, charts, genres, madeBy);
        }


    }
}

