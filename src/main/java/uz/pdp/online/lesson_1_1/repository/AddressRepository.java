package uz.pdp.online.lesson_1_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.online.lesson_1_1.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
