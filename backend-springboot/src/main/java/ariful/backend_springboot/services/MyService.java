package ariful.backend_springboot.services;

import ariful.backend_springboot.models.MyEntity;
import ariful.backend_springboot.repositories.MyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {
    private final MyRepository myRepository;

    public MyService(MyRepository myRepository) {
        this.myRepository = myRepository;
    }

    public List<MyEntity> findAll() {
        return myRepository.findAll();
    }
}
