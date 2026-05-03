import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.app.Application;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class Challenge003Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnBadRequestForIllegalArgument() throws Exception {
        mockMvc.perform(get("/errors/bad-request"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Bad request: Invalid request payload"));
    }

    @Test
    void shouldReturnServerErrorForGenericException() throws Exception {
        mockMvc.perform(get("/errors/server-error"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal error: Unexpected failure"));
    }
}
