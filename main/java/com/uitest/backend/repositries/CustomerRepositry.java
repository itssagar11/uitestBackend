package com.uitest.backend.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uitest.backend.entity.Customer;

public interface CustomerRepositry extends JpaRepository<Customer,Long> {

    boolean existsByAccountNoAndIFSC(long accountNo, String ifsc);

}
