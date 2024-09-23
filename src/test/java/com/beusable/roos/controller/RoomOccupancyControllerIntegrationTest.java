package com.beusable.roos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.beusable.roos.TestBase;
import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.service.RoomOccupancyOptimisationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
class RoomOccupancyControllerIntegrationTest extends TestBase {

    static final String OCCUPANCY_URL = "/occupancy";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomOccupancyOptimisationService roomOccupancyOptimisationService;

    @Test
    void shouldAllocateWhenTwoAndSevenRoomsRequested() throws Exception {

        RoomAllocationRequest request = new RoomAllocationRequest(2, 7, POTENTIAL_GUESTS);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(2))
               .andExpect(jsonPath("$.revenuePremium").value(b(583)))
               .andExpect(jsonPath("$.usageEconomy").value(4))
               .andExpect(jsonPath("$.revenueEconomy").value(b("189.99")));
    }

    @Test
    void shouldAllocateWhenThreeAndThreeRoomsRequested() throws Exception {

        RoomAllocationRequest request = new RoomAllocationRequest(3, 3, POTENTIAL_GUESTS);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(3))
               .andExpect(jsonPath("$.revenuePremium").value(b(738)))
               .andExpect(jsonPath("$.usageEconomy").value(3))
               .andExpect(jsonPath("$.revenueEconomy").value(b("167.99")));
    }

    @Test
    void shouldAllocateWhenSevenAndFiveRoomsRequested() throws Exception {

        RoomAllocationRequest request = new RoomAllocationRequest(7, 5, POTENTIAL_GUESTS);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(6))
               .andExpect(jsonPath("$.revenuePremium").value(b(1054)))
               .andExpect(jsonPath("$.usageEconomy").value(4))
               .andExpect(jsonPath("$.revenueEconomy").value(b("189.99")));
    }

}