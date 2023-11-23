package capstone.activity.results;

public class CreateSetListResult {

    private final boolean success;
    private final String name;
    private final String id;

    private CreateSetListResult(boolean success, String name, String id) {
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
        return "CreateSetListResult{" +
                "id= " + id + '\'' +
                "name= " + name + '\'' +
                "success= " + success + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

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

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public CreateSetListResult build() { return new CreateSetListResult(success, name, id); }
    }
}
