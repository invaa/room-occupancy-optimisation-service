package com.beusable.roos.controller;

import com.beusable.roos.TestBase;
import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import com.beusable.roos.service.RoomOccupancyOptimisationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomOccupancyController.class)
class RoomOccupancyControllerTest extends TestBase {

    static final String OCCUPANCY_URL = "/occupancy";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomOccupancyOptimisationService roomOccupancyOptimisationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldAllocateWhenSevenAndFiveRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                7, 5, POTENTIAL_GUESTS
        );

        RoomAllocationResponse response = new RoomAllocationResponse(7, b(1054), 5, b("189.99"));

        given(roomOccupancyOptimisationService.allocate(any(RoomAllocationRequest.class))).willReturn(response);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(7))
               .andExpect(jsonPath("$.revenuePremium").value(b(1054)))
               .andExpect(jsonPath("$.usageEconomy").value(5))
               .andExpect(jsonPath("$.revenueEconomy").value(b("189.99")));
    }

    @Test
    void shouldAllocateWhenThreeAndThreeRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                3, 3, POTENTIAL_GUESTS
        );

        RoomAllocationResponse response = new RoomAllocationResponse(3, b(738), 3, b("167.99"));

        given(roomOccupancyOptimisationService.allocate(any(RoomAllocationRequest.class))).willReturn(response);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(3))
               .andExpect(jsonPath("$.revenuePremium").value(738))
               .andExpect(jsonPath("$.usageEconomy").value(3))
               .andExpect(jsonPath("$.revenueEconomy").value(167.99));
    }

    @Test
    void shouldAllocateWhenTwoAndSevenRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                2, 7, POTENTIAL_GUESTS
        );

        RoomAllocationResponse response = new RoomAllocationResponse(2, b(583), 7, b("265.99"));

        given(roomOccupancyOptimisationService.allocate(any(RoomAllocationRequest.class))).willReturn(response);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(2))
               .andExpect(jsonPath("$.revenuePremium").value(b(583)))
               .andExpect(jsonPath("$.usageEconomy").value(7))
               .andExpect(jsonPath("$.revenueEconomy").value(b("265.99")));
    }

    @Test
    void shouldBadRequestWhenMinusOnePremiumRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                -1, 2, POTENTIAL_GUESTS
        );

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBadRequestWhenMinusOneEconomyRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                1, -1, POTENTIAL_GUESTS
        );

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldBadRequestWhenZeroPaymentRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                0, 0, List.of(BigDecimal.ONE, BigDecimal.ZERO));

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAllocateWhenZeroRoomsRequested() throws Exception {
        RoomAllocationRequest request = new RoomAllocationRequest(
                0, 0, POTENTIAL_GUESTS
        );

        RoomAllocationResponse response = new RoomAllocationResponse(0, BigDecimal.ZERO, 0, BigDecimal.ZERO);

        given(roomOccupancyOptimisationService.allocate(any(RoomAllocationRequest.class))).willReturn(response);

        mockMvc.perform(post(OCCUPANCY_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.usagePremium").value(0))
               .andExpect(jsonPath("$.revenuePremium").value(BigDecimal.ZERO))
               .andExpect(jsonPath("$.usageEconomy").value(0))
               .andExpect(jsonPath("$.revenueEconomy").value(BigDecimal.ZERO));
    }
}
