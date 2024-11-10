package org.example.repositories;

import org.example.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarBrandsRepository extends JpaRepository<Brand, UUID> {
    List<Brand> findByIssueYear(int issueYear);
    List<Brand> findByName(String name);

}
