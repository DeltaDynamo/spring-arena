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
public class Challenge001Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnUserForKnownId() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":\"1\",\"name\":\"Alice\"}"));
    }

    @Test
    void shouldReturn404ForUnknownId() throws Exception {
        mockMvc.perform(get("/users/5"))
                .andExpect(status().isNotFound());
    }
}
