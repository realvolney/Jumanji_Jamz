package capstone.activity.results;

import capstone.models.SetListModel;

public class UpdateSetListResult {
    private SetListModel setList;

    private UpdateSetListResult(SetListModel setList) {
        this.setList = setList;
    }

    public SetListModel getSetList() {
        return setList;
    }

    @Override
    public String toString() {
        return "UpdateSetListResult{" +
                "setList=" + setList +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private SetListModel setList;

        public Builder withSetList(SetListModel setList) {
            this.setList = setList;
            return this;
        }

        public UpdateSetListResult build() {
            return new UpdateSetListResult(setList);
        }
    }
}
