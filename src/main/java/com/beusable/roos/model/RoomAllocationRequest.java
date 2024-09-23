package com.beusable.roos.model;

import java.math.BigDecimal;
import java.util.List;

public record RoomAllocationRequest(int premiumRooms, int economyRooms, List<BigDecimal> potentialGuests) { }