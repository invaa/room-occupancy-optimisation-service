package com.beusable.roos;

import org.springframework.boot.SpringApplication;

public class TestRoomOccupancyOptimisationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(RoomOccupancyOptimisationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
