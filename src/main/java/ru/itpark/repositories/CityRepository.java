package ru.itpark.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.models.City;

public interface CityRepository extends JpaRepository<City, Long>{
}
