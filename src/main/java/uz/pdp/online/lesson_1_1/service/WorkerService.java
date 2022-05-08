package uz.pdp.online.lesson_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_1.entity.Address;
import uz.pdp.online.lesson_1_1.entity.Department;
import uz.pdp.online.lesson_1_1.entity.Worker;
import uz.pdp.online.lesson_1_1.payload.ApiResponse;
import uz.pdp.online.lesson_1_1.payload.WorkerDto;
import uz.pdp.online.lesson_1_1.repository.AddressRepository;
import uz.pdp.online.lesson_1_1.repository.DepartmentRepository;
import uz.pdp.online.lesson_1_1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {
    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    public ResponseEntity getWorkers() {
        List<Worker> workerList = workerRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(workerList);
    }

    public ResponseEntity getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Worker id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalWorker.get());
    }

    public ResponseEntity addWorker(WorkerDto workerDto) {
        if (workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("This phone number already exists", false));

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department id not found already exists", false));

        Worker worker = new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());

        Address address = new Address();
        address.setStreet(workerDto.getStreet());
        address.setHomeNumber(workerDto.getHomeNumber());
        addressRepository.save(address);
        worker.setAddress(address);
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Worker added", true));
    }

    public ResponseEntity editWorker(Integer id, WorkerDto workerDto) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Worker id not found", false));

        if (workerRepository.existsByPhoneNumberAndIdNot(workerDto.getPhoneNumber(), id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("This phone number already exists", false));

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department id not found already exists", false));

        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        worker.setDepartment(optionalDepartment.get());

        Address address = worker.getAddress();
        address.setHomeNumber(workerDto.getHomeNumber());
        address.setStreet(workerDto.getStreet());
        addressRepository.save(address);

        worker.setAddress(address);
        workerRepository.save(worker);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Worker edited", true));
    }

    public ResponseEntity deleteWorkerById(Integer id) {
        try {
            workerRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Worker successfully deleted", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Worker id not found", false));
        }
    }
}
