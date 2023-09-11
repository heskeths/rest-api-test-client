import lombok.*;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@ToString(includeFieldNames=true)
@Data
public class Response {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS");
    private final Integer id;
    private final Long duration;
    private final ZonedDateTime time = ZonedDateTime.now();

    public Response() {
        this.id = 0;
        this.duration = 0L;
    }

    public Response(Integer id, Long duration) {
        this.id = id;
        this.duration = duration;
    }

    public String getFormatterDateTime() {
        return time.format(formatter);
    }
}
