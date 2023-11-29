package org.dcg.controller;

import org.dcg.dto.ApplicationDTO;
import org.dcg.dto.ApplicationUpdateDTO;
import org.dcg.dto.RejectionDTO;
import org.dcg.entity.Application;
import org.dcg.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public Page<Application> listApplications(@RequestParam(required = false) String name,
                                              @RequestParam(required = false) String state,
                                              Pageable pageable) {
        return applicationService.getApplications(name, state, pageable);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationDTO dto) {
        if (dto.getApplicationName() == null || dto.getApplicationContent() == null) {
            return ResponseEntity.badRequest().build();
        }
        Application savedApplication = applicationService.createApplication(dto);
        return ResponseEntity.ok(savedApplication);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody ApplicationUpdateDTO dto) {
        return applicationService.updateApplicationContent(id, dto.getApplicationContent())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id, @RequestBody RejectionDTO rejectionDTO) {
        if (rejectionDTO.getReason() == null || rejectionDTO.getReason().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean isDeleted = applicationService.deleteApplication(id, rejectionDTO.getReason());
        return isDeleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/reject/{id}")
    public ResponseEntity<Void> rejectApplication(@PathVariable Long id, @RequestBody RejectionDTO rejectionDTO) {
        if (rejectionDTO.getReason() == null || rejectionDTO.getReason().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        boolean isRejected = applicationService.rejectApplication(id, rejectionDTO.getReason());
        return isRejected ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

