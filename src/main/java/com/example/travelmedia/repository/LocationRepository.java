package com.example.travelmedia.repository;

import com.example.travelmedia.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Long> {
        Location findByName(String location);
}
