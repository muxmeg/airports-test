package com.airports.controllers;

import com.airports.services.RunwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.airports.App.REST_SERVICE_PREFIX;

@RestController
@RequestMapping(value = RunwaysController.REST_PATH, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class RunwaysController {
    static final String REST_PATH = REST_SERVICE_PREFIX + "runways";

    private final RunwayService runwayService;

    @Autowired
    public RunwaysController(RunwayService runwayService) {
        this.runwayService = runwayService;
    }

}