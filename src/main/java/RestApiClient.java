import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestApiClient {

    //private static final String urlString = "http://localhost:18101/post/request";
    private static final String urlString = "http://34.89.126.48:80/post/request";
    private static final String requestString = "<Request><id>10</id></Request>";

    public static void main(String[] args) {

        RestApiConnection connection = new RestApiConnection(urlString, requestString);

        int iterations = Integer.valueOf(args[0]);

        List<Response> responseList = new ArrayList<>(iterations);

        System.out.println("");

        for (int i = 0; i <= iterations; i++) {
            Response response = connection.sendRequest(i);
            if(i>0) {
                System.out.println(String.format("%d,%s,%d", response.getId(), response.getFormatterDateTime(), response.getDuration()));
                responseList.add(response);
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("");

        long averageDuration = responseList.stream()
                .collect(Collectors.averagingLong(Response::getDuration))
                .longValue();

        System.out.println(String.format("Average: %d", averageDuration));
        List<Long> durationList = responseList.stream()
                .map(response->response.getDuration())
                .sorted()
                .collect(Collectors.toList());


        System.out.println(String.format("Maximum: %d", durationList.get(durationList.size()-1)));

        System.out.println();

        System.out.println(String.format("90th percentile: %d", percentile(durationList, 90)));
        System.out.println(String.format("95th percentile: %d", percentile(durationList, 95)));
        System.out.println(String.format("90th percentile: %d", percentile(durationList, 99)));
        System.out.println(String.format("99.5th percentile: %d", percentile(durationList, 99.5)));
        System.out.println(String.format("99.9th percentile: %d", percentile(durationList, 99.9)));

        System.out.println();

        int levelToCheck = 10;
        System.out.println(String.format("%f (%d of %d records) above %d ms", percentageDurationLevel(durationList, levelToCheck), countDurationLevel(durationList, levelToCheck), durationList.size(), levelToCheck));
        levelToCheck = 20;
        System.out.println(String.format("%f (%d of %d records) above %d ms", percentageDurationLevel(durationList, levelToCheck), countDurationLevel(durationList, levelToCheck), durationList.size(), levelToCheck));
        levelToCheck = 50;
        System.out.println(String.format("%f (%d of %d records) above %d ms", percentageDurationLevel(durationList, levelToCheck), countDurationLevel(durationList, levelToCheck), durationList.size(), levelToCheck));
        levelToCheck = 100;
        System.out.println(String.format("%f (%d of %d records) above %d ms", percentageDurationLevel(durationList, levelToCheck), countDurationLevel(durationList, levelToCheck), durationList.size(), levelToCheck));
        levelToCheck = 250;
        System.out.println(String.format("%f (%d of %d records) above %d ms", percentageDurationLevel(durationList, levelToCheck), countDurationLevel(durationList, levelToCheck), durationList.size(), levelToCheck));

    }

    public static int countDurationLevel(List<Long> latencies, int level) {
        long countAboveLevel = latencies.stream()
                .filter(l -> l >= level*1000000)
                .count();
        return Math.toIntExact(countAboveLevel);
    }

    public static double percentageDurationLevel(List<Long> latencies, int level) {
        int countAboveLevel = countDurationLevel(latencies, level);
        return (double) countAboveLevel / latencies.size() * 100;
    }

    public static long percentile(List<Long> latencies, double percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index-1);
    }
}
