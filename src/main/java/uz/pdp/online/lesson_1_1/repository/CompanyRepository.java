package uz.pdp.online.lesson_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_1.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    boolean existsByCorpName(String corpName);
    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
    boolean existsByAddress_HomeNumberAndAddress_Street(Integer address_homeNumber, String address_street);
}
