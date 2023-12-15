package capstone.activity.requests;

public class GetAllChartsRequest {
    private final String id;
    private final int limit;

    private GetAllChartsRequest(String id, int limit) {

        this.id = id;
        this.limit = limit;
    }

    public String getId() {
        return id;
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public String toString() {
        return "GetAllChartsRequest{" +
                "id='" + id + '\'' +
                ", limit=" + limit +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String id;
        private int limit;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withLimit(int limit) {
            this.limit = limit;
            return this;
        }

        public GetAllChartsRequest build() {
            return new GetAllChartsRequest(id, limit);
        }
    }
}
