package com.parser.kladr.web.controllers;

import com.parser.kladr.services.GenerateService;
import com.parser.kladr.web.requests.GenerateRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class ParserController {

    @Autowired
    GenerateService generateService;

    @Operation(description = "Сгенерировать человеческий(или нет) справочник")
    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody GenerateRequest request) {
        generateService.generate(request);
        return ResponseEntity.ok("Done.");
    }
}
