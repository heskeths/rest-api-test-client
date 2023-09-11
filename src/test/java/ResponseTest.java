import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    Response testResponse = new Response(1, 1L);
    @org.junit.jupiter.api.Test
    void getFormatterDateTime() {
        System.out.println(testResponse.getFormatterDateTime());
    }

}