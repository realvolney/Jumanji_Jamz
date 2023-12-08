package capstone.activity.requests;

public class GetAllChartsRequest {
    private final String id;

    private GetAllChartsRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "GetAllChartsRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetAllChartsRequest build() {
            return new GetAllChartsRequest(id);
        }
    }
}
