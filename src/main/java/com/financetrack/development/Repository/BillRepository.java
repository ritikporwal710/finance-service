package com.financetrack.development.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.financetrack.development.model.Bill;

public interface BillRepository extends JpaRepository<Bill, UUID> {
    List<Bill> findAllByUser_Id(UUID userId);



    @Query(value = "SELECT * FROM bills WHERE user_id = :userId AND due_date BETWEEN CURRENT_DATE AND CURRENT_DATE + INTERVAL '30 days'", nativeQuery = true)
List<Bill> findBillsDueInNext30Days(UUID userId);

}
