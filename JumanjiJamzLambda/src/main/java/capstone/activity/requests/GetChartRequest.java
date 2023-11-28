package capstone.activity.requests;

public class GetChartRequest {

    private final String id;

    private GetChartRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /**
     * This method returns a string representation of the GetChartRequest object.
     *
     * @return a string representing the object.
     */
    @Override
    public String toString() {
        return "GetChartRequest{" +
                "id='" + id + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String id;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public GetChartRequest build() {
            return new GetChartRequest(id);
        }
    }
}
