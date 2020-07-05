package com.propscout.kapkatet.repository;

import com.propscout.kapkatet.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CenterRepository extends JpaRepository<Center, Integer>, JpaSpecificationExecutor<Center> {
}
