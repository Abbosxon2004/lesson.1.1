package uz.pdp.online.lesson_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_1.payload.DepartmentDto;
import uz.pdp.online.lesson_1_1.service.DepartmentService;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public ResponseEntity getDepartments() {
        return departmentService.getDepartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity getDepartmentById(@PathVariable Integer id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public ResponseEntity addDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity editDepartment(@PathVariable Integer id, @RequestBody DepartmentDto departmentDto) {
        return departmentService.editDepartment(id, departmentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartment(@PathVariable Integer id) {
        return departmentService.deleteDepartment(id);
    }
}
