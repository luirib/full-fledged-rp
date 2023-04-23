package pt.aveiro.nariz.fullfledgedrp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pt.aveiro.nariz.fullfledgedrp.security.TestSecurityConfiguration;

@WebMvcTest
@Import(TestSecurityConfiguration.class)
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void authenticatedUserCanDisplayIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
            andExpect(MockMvcResultMatchers.
                status().
                isOk());
    }

    @Test
    void anonymousUserCanDisplayIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
            andExpect(MockMvcResultMatchers.
                status().
                isOk());
    }
}