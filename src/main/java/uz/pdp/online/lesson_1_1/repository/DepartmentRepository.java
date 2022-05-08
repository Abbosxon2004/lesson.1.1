package uz.pdp.online.lesson_1_1.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.online.lesson_1_1.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    boolean existsByNameAndCompany_Id(String name, Integer company_id);
}
