package com.airline.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.CheckInAgent;
import com.airline.rw.repository.CheckInAgentRepository;

@Service
public class CheckInAgentService {

    @Autowired
    private CheckInAgentRepository agentRepo;

    public String saveCheckInAgent(CheckInAgent agent) {
        agentRepo.save(agent);
        return "Check in agent saved successfully";
    }

    public List<CheckInAgent> getAllCheckInAgents() {
        return agentRepo.findAll();
    }

    public Optional<CheckInAgent> getCheckInAgentById(UUID id) {
        return agentRepo.findById(id);
    }

    public String updateCheckInAgent(UUID id, CheckInAgent updated) {
        Optional<CheckInAgent> existing = agentRepo.findById(id);
        if (existing.isEmpty()) {
            return "The check in agent with that id does not exist";
        }
        CheckInAgent agent = existing.get();
        agent.setFirstName(updated.getFirstName());
        agent.setLastName(updated.getLastName());
        agent.setStaffNumber(updated.getStaffNumber());
        agent.setCounter(updated.getCounter());
        agentRepo.save(agent);
        return "Check in agent updated successfully";
    }

    public String deleteCheckInAgent(UUID id) {
        if (agentRepo.findById(id).isEmpty()) {
            return "The check in agent with that id does not exist";
        }
        agentRepo.deleteById(id);
        return "Check in agent deleted successfully";
    }
}
