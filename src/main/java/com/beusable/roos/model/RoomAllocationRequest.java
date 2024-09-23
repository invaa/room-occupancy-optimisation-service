package com.beusable.roos.model;

import com.beusable.roos.validation.ValidGuestPayments;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.util.List;

public record RoomAllocationRequest(
        @Min(value = 0, message = "Premium rooms must be 0 or more") int premiumRooms,
        @Min(value = 0, message = "Economy rooms must be 0 or more") int economyRooms,
        @ValidGuestPayments(message = "Potential guest payments must be positive") List<BigDecimal> potentialGuests
) { }