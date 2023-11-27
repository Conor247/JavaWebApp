package com.conor.website.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Controller
public class MainController {

    @RestController
    public static class MyController {

        @GetMapping("/hello")
        @CrossOrigin(origins = "http://localhost:3000")
        public ResponseEntity<String> hello() throws IOException {
            String file ="src/main/resources/textfiles/helloworld.txt";

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine = reader.readLine();
            reader.close();

            return ResponseEntity.ok(currentLine);
        }

        @GetMapping("/goodbye")
        public String goodbye() {
            return "Goodbye Cruel World!";
        }
    }
}
