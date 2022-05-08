package uz.pdp.online.lesson_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_1.payload.WorkerDto;
import uz.pdp.online.lesson_1_1.service.WorkerService;

@RestController
@RequestMapping(value = "/worker")
public class WorkerController {
    @Autowired
    WorkerService workerService;

    @GetMapping
    public ResponseEntity getWorkers(){
        return workerService.getWorkers();
    }

    @GetMapping("/{id}")
    public ResponseEntity getWorkerById(@PathVariable Integer id){
        return workerService.getWorkerById(id);
    }

    @PostMapping
    public ResponseEntity addWorker(@RequestBody WorkerDto workerDto){
        return workerService.addWorker(workerDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity editWorker(@PathVariable Integer id,@RequestBody WorkerDto workerDto){
        return workerService.editWorker(id,workerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteWorker(@PathVariable Integer id){
        return workerService.deleteWorkerById(id);
    }

}
