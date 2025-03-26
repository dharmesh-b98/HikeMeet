package FinalHikeFinder.FinalHikeFinder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import FinalHikeFinder.FinalHikeFinder.service.HikeSpotService;
import FinalHikeFinder.FinalHikeFinder.service.UserService;

@SpringBootApplication
public class FinalHikeFinderApplication implements CommandLineRunner{

	@Autowired HikeSpotService hikeSpotService;

	@Autowired UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(FinalHikeFinderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		hikeSpotService.initialiseHikeSpots();
		userService.initialiseUsers();
	}

}
