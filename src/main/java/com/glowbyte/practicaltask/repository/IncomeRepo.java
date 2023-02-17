package com.glowbyte.practicaltask.repository;

import com.glowbyte.practicaltask.enitity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepo extends JpaRepository<Income, Long> {}
