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
public class Challenge004Test {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnFirstPageOfUsers() throws Exception {
        mockMvc.perform(get("/users?page=0&size=2"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"name\":\"Alice\"},{\"id\":\"2\",\"name\":\"Bob\"}]"));
    }

    @Test
    void shouldFilterUsersByName() throws Exception {
        mockMvc.perform(get("/users?search=Charlie"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"3\",\"name\":\"Charlie\"}]"));
    }
}
