package com.conor.website.controller;

import com.conor.website.model.RockPaperScissorsRequest;
import com.conor.website.model.RockPaperScissorsResponse;
import com.conor.website.service.RockPaperScissorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RockPaperScissorsController {

    RockPaperScissorsService rockPaperScissorsService;

    public RockPaperScissorsController(@Autowired RockPaperScissorsService rockPaperScissorsService) {
        this.rockPaperScissorsService = rockPaperScissorsService;
    }

    @PostMapping("/rps")
    //CrossOrigin allows requests from other services, this one being the front end application.
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<RockPaperScissorsResponse> playRockPaperScissors(@RequestBody RockPaperScissorsRequest requestBody) {

        return rockPaperScissorsService.playRockPaperScissors(requestBody);

    }
}
