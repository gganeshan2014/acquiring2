package com.cg.sp.controller;

import com.cg.sp.model.request.AcquiringNonPosRequest;
import com.cg.sp.service.AcquiringService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AcquiringController {

    @Autowired
    AcquiringService acquiringService;
    private static final Logger LOG = LoggerFactory.getLogger(AcquiringController.class);

    @PostMapping(value = "/acquiringMada")
    public ResponseEntity<Object> createAcquiringDetails(@Valid @RequestBody AcquiringNonPosRequest acquiringNonPosRequest,
                                                         @RequestParam(defaultValue = "1") int pageNo,
                                                         @RequestParam(defaultValue = "10") int size) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(acquiringService.createNonPosResponse(acquiringNonPosRequest,pageNo - 1, size),
                httpHeaders, HttpStatus.OK);
    }
}
