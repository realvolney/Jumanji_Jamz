package capstone.activity.results;

public class CreateChartResult {

    private final boolean success;
    private final String name;
    private final String id;

    private CreateChartResult(boolean success, String name, String id) {
        this.success = success;
        name = name;
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


    public static class Builder {

    }
}
