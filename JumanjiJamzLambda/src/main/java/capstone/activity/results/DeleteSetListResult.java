package capstone.activity.results;

import capstone.activity.requests.DeleteSetListRequest;

public class DeleteSetListResult {
    private final Boolean success;

    private DeleteSetListResult(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "DeleteSetListResult{" +
                "success=" + success +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private Boolean success;

        public Builder withSuccess(Boolean success) {
            this.success = success;
            return this;
        }

        public DeleteSetListResult build() {
            return new DeleteSetListResult(success);
        }
    }
}
