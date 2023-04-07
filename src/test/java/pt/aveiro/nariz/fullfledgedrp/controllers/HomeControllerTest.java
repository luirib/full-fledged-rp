package pt.aveiro.nariz.fullfledgedrp.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class HomeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void can_display_index_page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).
            andExpect(MockMvcResultMatchers.
                status().
                isOk());
    }
}