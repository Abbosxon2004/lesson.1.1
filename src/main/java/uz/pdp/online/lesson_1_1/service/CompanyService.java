package uz.pdp.online.lesson_1_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.online.lesson_1_1.entity.Address;
import uz.pdp.online.lesson_1_1.entity.Company;
import uz.pdp.online.lesson_1_1.payload.ApiResponse;
import uz.pdp.online.lesson_1_1.payload.CompanyDto;
import uz.pdp.online.lesson_1_1.repository.AddressRepository;
import uz.pdp.online.lesson_1_1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    AddressRepository addressRepository;

    public ResponseEntity getCompanies() {
        final List<Company> companyList = companyRepository.findAll();
        return ResponseEntity.ok(companyList);
    }

    public ResponseEntity getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Bunday id mavjud emas", false));
        return ResponseEntity.status(HttpStatus.FOUND).body(optionalCompany.get());
    }

    public ResponseEntity addCompany(CompanyDto companyDto) {
        if (companyRepository.existsByCorpName(companyDto.getCorpName()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("CorpName already exists.", false));
        if (companyRepository.existsByAddress_HomeNumberAndAddress_Street(companyDto.getHomeNumber(), companyDto.getStreet()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("Address already exists", false));

        Company company = new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Address address = new Address();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        Address savedAddress = addressRepository.save(address);

        company.setAddress(savedAddress);
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Company added", true));
    }

    public ResponseEntity editCompany(Integer id, CompanyDto companyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company id not found", false));
        if (companyRepository.existsByCorpNameAndIdNot(companyDto.getCorpName(), id))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse("CorpName already exists.", false));
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());

        Address address = company.getAddress();
        address.setStreet(companyDto.getStreet());
        address.setHomeNumber(companyDto.getHomeNumber());
        addressRepository.save(address);

        company.setAddress(address);
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Company information edited", true));
    }

    public ResponseEntity deleteCompany(Integer id) {
        if (!companyRepository.existsById(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Company id not found", false));
        companyRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ApiResponse("Company id not found", true));
    }
}
