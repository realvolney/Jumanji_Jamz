package capstone.models;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class SetListModel {
    private UUID id;
    private String name;
    private Set<String> charts;
    private Set<String> genres;
    private String madeBy;

    private SetListModel(UUID id, String name, Set<String> charts, Set<String> genres, String madeBy) {
        this.id = id;
        this.name = name;
        this.charts = charts;
        this.genres = genres;
        this.madeBy = madeBy;
    }

    public UUID getId() {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetListModel that = (SetListModel) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(charts, that.charts) && Objects.equals(genres, that.genres) && Objects.equals(madeBy, that.madeBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, charts, genres, madeBy);
    }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private UUID id;
        private String name;
        private Set<String> charts;
        private Set<String> genres;
        private String madeBy;

        public Builder withId(UUID id) {
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

