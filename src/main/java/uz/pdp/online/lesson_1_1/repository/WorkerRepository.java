package uz.pdp.online.lesson_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_1_1.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
    boolean existsByPhoneNumber(Integer phoneNumber);
    boolean existsByPhoneNumberAndIdNot(Integer phoneNumber, Integer id);
}
