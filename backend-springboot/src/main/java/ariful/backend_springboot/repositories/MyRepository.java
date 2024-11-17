package ariful.backend_springboot.repositories;

import ariful.backend_springboot.models.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyRepository extends JpaRepository<MyEntity, Long> {
}
