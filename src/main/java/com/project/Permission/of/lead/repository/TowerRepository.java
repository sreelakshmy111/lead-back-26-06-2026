package com.project.Permission.of.lead.repository;

import com.project.Permission.of.lead.entity.Tower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TowerRepository extends JpaRepository<Tower,Long> {
//    Object findByTowerName(String towerId);

    Optional<Tower> findByName(String towerId);
}
