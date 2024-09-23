package com.beusable.roos.service;

import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class RoomOccupancyOptimisationServiceTest {

    @InjectMocks
    private final RoomOccupancyOptimisationService roomOccupancyService = new RoomOccupancyOptimisationService();

    private static final List<BigDecimal> POTENTIAL_GUESTS = List.of(
            b(23), b(45), b(155), b(374), b(22),
            b("99.99"), b(100), b(101), b(115), b(209));

    @Test
    void shouldAllocateWhenSevenAndFiveRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(7, 5, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isEqualTo(6);
        assertThat(response.revenuePremium()).isEqualTo(b(1054));
        assertThat(response.usageEconomy()).isEqualTo(4);
        assertThat(response.revenueEconomy()).isEqualTo(b("189.99"));
    }

    @Test
    void shouldAllocateWhenThreeAndThreeRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(3, 3, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isEqualTo(3);
        assertThat(response.revenuePremium()).isEqualTo(b(738));
        assertThat(response.usageEconomy()).isEqualTo(3);
        assertThat(response.revenueEconomy()).isEqualTo(b("167.99"));
    }

    @Test
    void shouldAllocateWhenTwoAndSevenRoomsRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(2, 7, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isEqualTo(2);
        assertThat(response.revenuePremium()).isEqualTo(b(583));
        assertThat(response.usageEconomy()).isEqualTo(4);
        assertThat(response.revenueEconomy()).isEqualTo(b("189.99"));
    }

    @Test
    void shouldAllocateWhenZeroZeroRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(0, 0, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isZero();
        assertThat(response.revenuePremium()).isEqualTo(b(0));
        assertThat(response.usageEconomy()).isZero();
        assertThat(response.revenueEconomy()).isEqualTo(b(0));
    }

    @Test
    void shouldAllocateWhenMinusOneMinusOneRequested() {
        RoomAllocationRequest request = new RoomAllocationRequest(-1, -1, POTENTIAL_GUESTS);

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isZero();
        assertThat(response.revenuePremium()).isEqualTo(b(0));
        assertThat(response.usageEconomy()).isZero();
        assertThat(response.revenueEconomy()).isEqualTo(b(0));
    }

    @Test
    void shouldAllocateWhenMinusOneMinusOneRequestedAndNoGuests() {
        RoomAllocationRequest request = new RoomAllocationRequest(-1, -1, List.of());

        RoomAllocationResponse response = roomOccupancyService.allocate(request);

        assertThat(response.usagePremium()).isZero();
        assertThat(response.revenuePremium()).isEqualTo(b(0));
        assertThat(response.usageEconomy()).isZero();
        assertThat(response.revenueEconomy()).isEqualTo(b(0));
    }

    private static BigDecimal b(double value) {
        return new BigDecimal(value);
    }

    private static BigDecimal b(String value) {
        return new BigDecimal(value);
    }
}