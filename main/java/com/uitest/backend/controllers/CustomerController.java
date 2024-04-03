package com.uitest.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.uitest.backend.dto.BalanceDTO;
import com.uitest.backend.dto.ChequeDto;
import com.uitest.backend.dto.TransactionDTO;
import com.uitest.backend.entity.Customer;
import com.uitest.backend.repositries.CustomerRepositry;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@CrossOrigin("http://localhost:3000")
public class CustomerController {


    @Autowired
    private CustomerRepositry customerRepository;

     @PostMapping("/opening-account")
    public ResponseEntity<String> newCustomer(@RequestBody Customer customer) {
        if (customerRepository.existsById(customer.getAccountNo())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer already exists");
        }

        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<String> withdrawal(@RequestBody TransactionDTO transaction) {
        if (!customerRepository.existsByAccountNoAndIFSC(transaction.accountNo(), transaction.IFSC())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        Customer customer = customerRepository.findById(transaction.accountNo()).get();

        if (customer.getAmount() < transaction.amount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        }

        customer.setAmount(customer.getAmount() - transaction.amount());
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.OK).body("Withdrawal successful");
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody TransactionDTO transaction) {
        if (!customerRepository.existsByAccountNoAndIFSC(transaction.accountNo(), transaction.IFSC())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        }

        Customer customer = customerRepository.findById(transaction.accountNo()).get();

       

        customer.setAmount(customer.getAmount() + transaction.amount());
        customerRepository.save(customer);

        return ResponseEntity.status(HttpStatus.OK).body("Deposit successful");
    }

    @PostMapping("/cheque-deposit")
    public ResponseEntity<String> chequeDeposit(@RequestBody ChequeDto entity) {
        if (!customerRepository.existsByAccountNoAndIFSC(entity.from_accountNo(), entity.from_IFSC())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sender not found");
        }

        if (!customerRepository.existsByAccountNoAndIFSC(entity.to_accountNo(), entity.to_IFSC())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receiver not found");
        }
        Customer sender = customerRepository.findById(entity.from_accountNo()).get();
        Customer receiver = customerRepository.findById(entity.to_accountNo()).get();

        if (sender.getAmount() < entity.amount()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds in sender Account");
        }
        sender.setAmount(sender.getAmount()-entity.amount());
        receiver.setAmount(receiver.getAmount()+entity.amount());
        
        customerRepository.save(sender);
        customerRepository.save(receiver);
        return ResponseEntity.status(HttpStatus.OK).body("Cheque Deposit successful");
    }
    
    @GetMapping("/balance-enquiry")
    public ResponseEntity<String> getBalance(@RequestParam long accountNo, @RequestParam String IFSC) {
        if (!customerRepository.existsByAccountNoAndIFSC(accountNo,IFSC)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");

            
        }


        Customer customer = customerRepository.findById(accountNo).get();

        return ResponseEntity.status(HttpStatus.OK).body("Balance: "+customer.getAmount());

    }
    

}
