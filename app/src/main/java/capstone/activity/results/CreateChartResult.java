package capstone.activity.results;

public class CreateChartResult {

    private final boolean success;
    private final String name;
    private final String id;

    private CreateChartResult(boolean success, String name, String id) {
        this.success = success;
        this.name = name;
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CreateChartResult{" +
                "id= " + id + '\'' +
                "name= " + name + '\'' +
                "success= " + success + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Boolean success;
        private String name;
        private String id;

        public Builder withSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public  Builder withId(String id) {
            this.id = id;
            return this;
        }

        public CreateChartResult build() {
            return new CreateChartResult(success, name, id);
        }
    }
}
