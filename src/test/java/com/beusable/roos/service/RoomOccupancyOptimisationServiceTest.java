package com.beusable.roos.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.beusable.roos.TestBase;
import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class RoomOccupancyOptimisationServiceTest extends TestBase {

    @InjectMocks
    private final RoomOccupancyOptimisationService roomOccupancyService = new RoomOccupancyOptimisationService();

    @Test
    void shouldAllocateWhenSevenAndFiveRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(7, 5, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isEqualTo(6);
        assertThat(response.getRevenuePremium()).isEqualTo(b(1054));
        assertThat(response.getUsageEconomy()).isEqualTo(4);
        assertThat(response.getRevenueEconomy()).isEqualTo(b("189.99"));
    }

    @Test
    void shouldAllocateWhenThreeAndThreeRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(3, 3, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isEqualTo(3);
        assertThat(response.getRevenuePremium()).isEqualTo(b(738));
        assertThat(response.getUsageEconomy()).isEqualTo(3);
        assertThat(response.getRevenueEconomy()).isEqualTo(b("167.99"));
    }

    @Test
    void shouldAllocateWhenTwoAndSevenRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(2, 7, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isEqualTo(2);
        assertThat(response.getRevenuePremium()).isEqualTo(b(583));
        assertThat(response.getUsageEconomy()).isEqualTo(4);
        assertThat(response.getRevenueEconomy()).isEqualTo(b("189.99"));
    }

    @Test
    void shouldAllocateWhenZeroZeroRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(0, 0, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isZero();
        assertThat(response.getRevenuePremium()).isEqualTo(b(0));
        assertThat(response.getUsageEconomy()).isZero();
        assertThat(response.getRevenueEconomy()).isEqualTo(b(0));
    }

    @Test
    void shouldAllocateWhenMinusOneMinusOneRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(-1, -1, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isZero();
        assertThat(response.getRevenuePremium()).isEqualTo(b(0));
        assertThat(response.getUsageEconomy()).isZero();
        assertThat(response.getRevenueEconomy()).isEqualTo(b(0));
    }

    @Test
    void shouldAllocateWhenMinusOneMinusOneRequestedAndNoGuests() {
        RoomAllocationRequest request = new RoomAllocationRequest(-1, -1, List.of());

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.getUsagePremium()).isZero();
        assertThat(response.getRevenuePremium()).isEqualTo(b(0));
        assertThat(response.getUsageEconomy()).isZero();
        assertThat(response.getRevenueEconomy()).isEqualTo(b(0));
    }
}
