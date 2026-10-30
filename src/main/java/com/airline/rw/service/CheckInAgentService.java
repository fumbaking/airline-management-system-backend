package com.airline.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.rw.domain.CheckInAgent;
import com.airline.rw.domain.Counter;
import com.airline.rw.domain.Language;
import com.airline.rw.repository.CheckInAgentRepository;
import com.airline.rw.repository.CounterRepository;
import com.airline.rw.repository.LanguageRepository;

@Service
public class CheckInAgentService {

    @Autowired
    private CheckInAgentRepository agentRepo;

    @Autowired
    private CounterRepository counterRepo;

    @Autowired
    private LanguageRepository languageRepo;

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

    public List<CheckInAgent> getAgentsByLanguage(String name) {
        return agentRepo.findByLanguageName(name);
    }

    public String addLanguage(UUID checkInAgentId, UUID languageId) {
        Optional<CheckInAgent> agentOpt = agentRepo.findById(checkInAgentId);
        if (agentOpt.isEmpty()) {
            return "The check in agent with that id does not exist";
        }
        Optional<Language> languageOpt = languageRepo.findById(languageId);
        if (languageOpt.isEmpty()) {
            return "That language does not exist";
        }
        CheckInAgent agent = agentOpt.get();
        Language language = languageOpt.get();
        if (agent.getLanguages().contains(language)) {
            return "The check in agent already has that language";
        }
        agent.getLanguages().add(language);
        agentRepo.save(agent);
        return "Language added to the check in agent";
    }

    public String moveToCounter(UUID checkInAgentId, int counterNumber) {
        Optional<CheckInAgent> agentOpt = agentRepo.findById(checkInAgentId);
        if (agentOpt.isEmpty()) {
            return "The check in agent with that id does not exist";
        }
        Optional<Counter> counterOpt = counterRepo.findByCounterNumber(counterNumber);
        if (counterOpt.isEmpty()) {
            return "The counter with that counterNumber does not exist";
        }
        Counter counter = counterOpt.get();
        CheckInAgent agent = agentOpt.get();
        if (!counter.equals(agent.getCounter()) && agentRepo.existsByCounter(counter)) {
            return "That counter is taken by another check in agent";
        }
        agent.setCounter(counter);
        agentRepo.save(agent);
        return "Counter changed successfully";
    }
}
