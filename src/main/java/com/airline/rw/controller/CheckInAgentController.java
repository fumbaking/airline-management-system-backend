package com.airline.rw.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.airline.rw.domain.CheckInAgent;
import com.airline.rw.service.CheckInAgentService;

@RestController
@RequestMapping("/api/check-in-agents")
public class CheckInAgentController {

    @Autowired
    private CheckInAgentService agentService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCheckInAgent(@RequestBody CheckInAgent agent) {
        String message = agentService.saveCheckInAgent(agent);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CheckInAgent>> getAllCheckInAgents() {
        return new ResponseEntity<>(agentService.getAllCheckInAgents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCheckInAgentById(@PathVariable UUID id) {
        return agentService.getCheckInAgentById(id)
                .<ResponseEntity<?>>map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElse(new ResponseEntity<>("The check in agent with that id does not exist", HttpStatus.NOT_FOUND));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCheckInAgent(@PathVariable UUID id, @RequestBody CheckInAgent agent) {
        String message = agentService.updateCheckInAgent(id, agent);
        if (message.equals("Check in agent updated successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCheckInAgent(@PathVariable UUID id) {
        String message = agentService.deleteCheckInAgent(id);
        if (message.equals("Check in agent deleted successfully")) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
}
