package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationDetails;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class for compensation type uses two REST endpoints
 *          create : Accepts Request body containing salary and effectiveDate
 *                 : Accepts employee id
 *                 : returns fully filled compensation structure
 *
 *           read  : Accepts employee id
 *                 : returns compensation structure for existing employee
 */
@RestController
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;


    @PostMapping("/employee/{id}/compensation")
    public Compensation create(@PathVariable String id,@RequestBody CompensationDetails compensationDetailsObject ){
        LOG.debug("Received compensation details, creating compensating structure [{}]", id);

        return compensationService.create(id,compensationDetailsObject.getSalary(),compensationDetailsObject.getEffectiveDate());
    }

    @GetMapping("/employee/{id}/compensation")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received employee id, fetching compensation details [{}]", id);

        return compensationService.read(id);
    }
}
