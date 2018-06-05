package com.tmrnd.csvloader;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.tmrnd.entities.Task;
import com.tmrnd.entities.Team;
import com.tmrnd.entities.TeamSkill;
import com.tmrnd.repositories.AssignmentResultRepository;
import com.tmrnd.repositories.TaskRepository;
import com.tmrnd.repositories.TeamRepository;
import com.tmrnd.repositories.TeamSkillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * Created by kent on 05/06/2018.
 */
@Service
public class CSVLoader {

    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Value("${others.csv.directory}")
    private String directory;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamSkillRepository teamSkillRepository;

    @Autowired
    private AssignmentResultRepository assignmentResultRepository;

    public <T> void watchDirectoryPath(Path path, String filename, long threadId, Class<T> t) {
        try {
            Boolean isFolder = (Boolean) Files.getAttribute(path, "basic:isDirectory", NOFOLLOW_LINKS);
            if (!isFolder) {
                throw new IllegalArgumentException("Path: " + path + " is not a folder");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        log.info("Thread " + threadId + " watching path: " + path + ", watching file: " + filename);

        FileSystem fs = path.getFileSystem();

        try (WatchService service = fs.newWatchService()) {
            path.register(service, ENTRY_CREATE);

            WatchKey key = null;
            while (true) {
                key = service.take();

                WatchEvent.Kind<?> kind = null;
                for (WatchEvent<?> watchEvent : key.pollEvents()) {
                    kind = watchEvent.kind();
                    if (OVERFLOW == kind) {
                        continue; //loop
                    } else if (ENTRY_CREATE == kind) {
                        Path newPath = ((WatchEvent<Path>) watchEvent).context();
                        if (newPath.toString().equals(filename)) {
                            log.info("Thread " + threadId + " detected new file: " + newPath);
                            this.loadObjectList(t, path.toString() + fs.getSeparator() + newPath);

                            log.info("### Completed: " + newPath);
                        }
                    }
                }
                if (!key.reset()) {
                    break; //loop
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    @Async
    public void processTask() throws InterruptedException {
        log.info("### Start Processing with Thread id: " + Thread.currentThread().getId());

        watchDirectoryPath(getPath(), "task.csv", Thread.currentThread().getId(), Task.class);

        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
    }

    @Async
    public void processTeam() throws InterruptedException {
        log.info("### Start Processing with Thread id: " + Thread.currentThread().getId());

        watchDirectoryPath(getPath(), "team.csv", Thread.currentThread().getId(), Team.class);

        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
    }

    @Async
    public void processTeamSkill() throws InterruptedException {
        log.info("### Start Processing with Thread id: " + Thread.currentThread().getId());

        watchDirectoryPath(getPath(), "team_skill.csv", Thread.currentThread().getId(), TeamSkill.class);

        String processInfo = String.format("Processing is Done with Thread id= %d", Thread.currentThread().getId());
    }

    public Path getPath() {
        return Paths.get(directory);
    }

    public <T> void loadObjectList(Class<T> type, String fileName) {
        log.info("### Retrieving: " + fileName);
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new File(fileName);
            MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(file);

            List<T> list = readValues.readAll();

            if (!list.isEmpty()) {
                if (type.getName().equals(Team.class.getName())) saveTeam((List<Team>) list);
                else if (type.getName().equals(Task.class.getName())) saveTask((List<Task>) list);
                else if (type.getName().equals(TeamSkill.class.getName()))
                    saveTeamSkill((List<TeamSkill>) list);
            }

            log.info("### Deleting: " + fileName);
            file.delete();
            taskAssignment();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
        }
    }

    public void saveTeam(List<Team> list) {
        log.info("### Saving Team to Database");
        list.forEach(team -> {
            Team t = teamRepository.getByTeamId(team.getTeamId());

            if (t != null) {
                log.info("### Ignoring duplicate entry ['" + t.getTeamId() + "']");
            } else {
                teamRepository.save(team);
            }
        });
    }

    public void saveTask(List<Task> list) {
        log.info("### Saving Task to Database");
        list.forEach(task -> {
            Task t = taskRepository.getByTaskId(task.getTaskId());

            if (t != null) {
                log.info("### Ignoring duplicate entry ['" + t.getTaskId() + "']");
            } else {
                taskRepository.save(task);
            }
        });
    }

    public void saveTeamSkill(List<TeamSkill> list) {
        log.info("### Saving Team Skill to Database");
        teamSkillRepository.saveAll(list);
    }

    public void taskAssignment() {
        log.info("### Assigning Task");
        assignmentResultRepository.assignTask();
    }
}
