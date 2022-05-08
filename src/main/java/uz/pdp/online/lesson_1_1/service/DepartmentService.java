package uz.pdp.online.lesson_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_1.entity.Company;
import uz.pdp.online.lesson_1_1.entity.Department;
import uz.pdp.online.lesson_1_1.payload.ApiResponse;
import uz.pdp.online.lesson_1_1.payload.DepartmentDto;
import uz.pdp.online.lesson_1_1.repository.CompanyRepository;
import uz.pdp.online.lesson_1_1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    public ResponseEntity getDepartments() {
        List<Department> departmentList = departmentRepository.findAll();
        return ResponseEntity.status(HttpStatus.FOUND).body(departmentList);
    }

    public ResponseEntity getDepartmentById(Integer id) {
        final Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department id not found", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalDepartment.get());
    }

    public ResponseEntity addDepartment(DepartmentDto departmentDto) {
        if (departmentRepository.existsByNameAndCompany_Id(departmentDto.getName(), departmentDto.getCompanyId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("In this company this department already exists", false));

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company id not found",false));
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Depertment successfully added",true));
    }

    public ResponseEntity editDepartment(Integer id,DepartmentDto departmentDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department id not found",false));

        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company id not found",false));

        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Department edited.",true));
    }

    public ResponseEntity deleteDepartment(Integer id){
        try {
            departmentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Department deleted",true));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Department id not found",false));
        }
    }

}
