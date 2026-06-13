package com.pharma.qc.controller;

import com.pharma.qc.model.Inspection;
import com.pharma.qc.service.InspectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/qc/inspections")
public class InspectionController {

    private final InspectionService service;

    public InspectionController(InspectionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Inspection>> getAll(
            @RequestParam(required = false) String result,
            @RequestParam(required = false) String batchNumber) {

        if (result != null) {
            return ResponseEntity.ok(service.findByResult(result));
        }

        if (batchNumber != null) {
            return ResponseEntity.ok(service.findByBatchNumber(batchNumber));
        }

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inspection> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Inspection> create(@Valid @RequestBody Inspection inspection) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(inspection));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inspection> update(
            @PathVariable Long id,
            @Valid @RequestBody Inspection inspection) {
        return ResponseEntity.ok(service.update(id, inspection));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
                Map.of("message", "Inspection deleted successfully"));
    }
}
