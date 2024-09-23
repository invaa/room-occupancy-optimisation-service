package com.beusable.roos.controller;

import com.beusable.roos.api.OccupancyApi;
import com.beusable.roos.model.RoomAllocationRequest;
import com.beusable.roos.model.RoomAllocationResponse;
import com.beusable.roos.service.RoomOccupancyOptimisationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
@Slf4j
public class RoomOccupancyController implements OccupancyApi {
    private final RoomOccupancyOptimisationService roomOccupancyOptimisationService;

    @Override
    public ResponseEntity<RoomAllocationResponse> occupancyPost(RoomAllocationRequest roomAllocationRequest) {
        if (log.isInfoEnabled()) {
            log.info("Request to allocateRooms : {}", roomAllocationRequest);
        }
        return new ResponseEntity<>(roomOccupancyOptimisationService.allocate(roomAllocationRequest), HttpStatus.OK);
    }
}
