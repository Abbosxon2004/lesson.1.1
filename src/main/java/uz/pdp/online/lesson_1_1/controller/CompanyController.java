package uz.pdp.online.lesson_1_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.lesson_1_1.payload.CompanyDto;
import uz.pdp.online.lesson_1_1.service.CompanyService;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity getCompanies() {
        ResponseEntity companies = companyService.getCompanies();
        return companies;
    }

    @GetMapping("/{id}")
    public ResponseEntity getCompanyById(@PathVariable Integer id) {
        ResponseEntity companyById = companyService.getCompanyById(id);
        return companyById;
    }

    @PostMapping
    public ResponseEntity addCompany(@RequestBody CompanyDto companyDto) {
        ResponseEntity addCompany = companyService.addCompany(companyDto);
        return addCompany;
    }

    @PutMapping("/{id}")
    public ResponseEntity editCompany(@RequestBody CompanyDto companyDto, @PathVariable Integer id) {
        ResponseEntity editCompany = companyService.editCompany(id, companyDto);
        return editCompany;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCompany(@PathVariable Integer id) {
        ResponseEntity deleteCompany = companyService.deleteCompany(id);
        return deleteCompany;
    }
}
