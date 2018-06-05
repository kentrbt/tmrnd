package com.tmrnd;

import com.tmrnd.entities.AssignmentResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void testRestService() {
        final String uri = "http://localhost:8080/assign-tasks";
        RestTemplate restTemplate = new RestTemplate();

        AssignmentResult assignmentResult = new AssignmentResult();
        assignmentResult.setTeamId("team99");
        assignmentResult.setTaskId("task99");
        assignmentResult.setSkill("skill99");

        restTemplate.postForEntity(uri, assignmentResult, AssignmentResult.class);
    }

}
