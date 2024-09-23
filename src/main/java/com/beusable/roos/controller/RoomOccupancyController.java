package com.beusable.roos.controller;

import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import com.beusable.roos.service.RoomOccupancyOptimisationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/occupancy")
@AllArgsConstructor
@Slf4j
public class RoomOccupancyController {
    private final RoomOccupancyOptimisationService roomOccupancyOptimisationService;

    @PostMapping
    public RoomAllocationResponse allocateRooms(@RequestBody @Valid RoomAllocationRequest request) {
        if (log.isInfoEnabled()) {
            log.info("Request to allocateRooms : {}", request);
        }
        return roomOccupancyOptimisationService.allocate(request);
    }
}
