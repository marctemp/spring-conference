package com.pluralsight.conference.controllers;

import com.pluralsight.conference.models.Session;
import com.pluralsight.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @Value("${spring.datasource.url}")
    private String databaseURL;

    @GetMapping
    public List<Session> list(){
        System.out.println("databaseURL = " + databaseURL);
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session get(@PathVariable Long id){
        return sessionRepository.getById(id);
    }

    @PostMapping
    public Session create(@RequestBody Session session){
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        sessionRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Session update(@PathVariable Long id, @RequestBody Session session){
        Session existingSession = sessionRepository.getById(id);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }
}
