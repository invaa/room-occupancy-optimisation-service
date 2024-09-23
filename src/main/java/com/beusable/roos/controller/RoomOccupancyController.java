package com.beusable.roos.controller;

import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import com.beusable.roos.service.RoomOccupancyOptimisationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/occupancy")
@AllArgsConstructor
public class RoomOccupancyController {
    private final RoomOccupancyOptimisationService roomOccupancyOptimisationService;

    @PostMapping
    public RoomAllocationResponse allocateRooms(@RequestBody RoomAllocationRequest request) {
        return roomOccupancyOptimisationService.allocate(request);
    }
}
