package com.beusable.roos.service;

import static com.beusable.roos.config.DefaultConstants.PREMIUM_PRICE_THRESHOLD;

import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.stereotype.Service;

@Service
public class RoomOccupancyOptimisationService {

    public RoomAllocationResponse allocate(RoomAllocationRequest request) {
        var guestPayments = new ArrayList<>(request.potentialGuests());
        guestPayments.sort(Collections.reverseOrder());

        var premiumRoomsOccupied = 0;
        var economyRoomsOccupied = 0;
        var revenuePremium = BigDecimal.ZERO;
        var revenueEconomy = BigDecimal.ZERO;

        for (var payment : guestPayments) {
            if (isPremiumGuest(payment)) {
                if (isPremiumRoomsAvailable(request, premiumRoomsOccupied)) {
                    premiumRoomsOccupied++;
                    revenuePremium = revenuePremium.add(payment);
                }
            } else {
                if (isEconomyRoomsAvailable(request, economyRoomsOccupied)) {
                    economyRoomsOccupied++;
                    revenueEconomy = revenueEconomy.add(payment);
                } else if (isPremiumRoomsAvailable(request, premiumRoomsOccupied)) {
                    // Upgrade economy guests to premium rooms
                    premiumRoomsOccupied++;
                    revenuePremium = revenuePremium.add(payment);
                }
            }
        }
        return new RoomAllocationResponse(premiumRoomsOccupied, revenuePremium, economyRoomsOccupied, revenueEconomy);
    }

    private boolean isEconomyRoomsAvailable(RoomAllocationRequest request, int economyRoomsOccupied) {
        return economyRoomsOccupied < request.economyRooms();
    }

    private boolean isPremiumRoomsAvailable(RoomAllocationRequest request, int premiumRoomsOccupied) {
        return premiumRoomsOccupied < request.premiumRooms();
    }

    private boolean isPremiumGuest(BigDecimal payment) {
        return PREMIUM_PRICE_THRESHOLD.compareTo(payment) <= 0;
    }
}
