package com.conor.website.controller;

import com.conor.website.model.RockPaperScissorsRequest;
import com.conor.website.model.RockPaperScissorsResponse;
import com.conor.website.service.RockPaperScissorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RockPaperScissorsController {

    RockPaperScissorsService rockPaperScissorsService;

    public RockPaperScissorsController(@Autowired RockPaperScissorsService rockPaperScissorsService) {
        this.rockPaperScissorsService = rockPaperScissorsService;
    }

    @PostMapping("/rps")
    public ResponseEntity<RockPaperScissorsResponse> createResource(@RequestBody RockPaperScissorsRequest requestBody) {

        return rockPaperScissorsService.playRockPaperScissors(requestBody);

    }

}
