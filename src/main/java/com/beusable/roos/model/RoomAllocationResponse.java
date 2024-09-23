package com.beusable.roos.model;

import java.math.BigDecimal;

public record RoomAllocationResponse(
        int usagePremium, BigDecimal revenuePremium, int usageEconomy, BigDecimal revenueEconomy) {}
