package com.tmrnd;

import com.tmrnd.csvloader.CSVLoader;
import com.tmrnd.entities.AssignmentResult;
import com.tmrnd.repositories.AssignmentResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@SpringBootApplication
@RestController
@EnableAsync
public class DemoApplication {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private CSVLoader csvLoader;

    @Autowired
    private AssignmentResultRepository assignmentResultRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String get() {
        return "hello world!";
    }

    @RequestMapping(value = "/assign-tasks", method = RequestMethod.POST)
    public AssignmentResult assignTasks(@Valid @RequestBody AssignmentResult assignmentResult) {
        log.info("### Execute assignTasks " + assignmentResult.toString());
        return assignmentResultRepository.save(assignmentResult);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void startJob() throws InterruptedException {
        csvLoader.processTask();
        csvLoader.processTeam();
        csvLoader.processTeamSkill();
    }
}
