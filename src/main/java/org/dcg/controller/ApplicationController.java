package org.dcg.controller;

import org.dcg.dto.ApplicationDTO;
import org.dcg.entity.Application;
import org.dcg.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationDTO dto) {
        if (dto.getApplicationName() == null || dto.getApplicationContent() == null) {
            return ResponseEntity.badRequest().build();
        }

        Application savedApplication = applicationService.createApplication(dto);
        return ResponseEntity.ok(savedApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application applicationDetails) {
        return applicationService.updateApplicationContent(id, applicationDetails.getApplicationContent())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}

