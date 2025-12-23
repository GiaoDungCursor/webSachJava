package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.model.Customer;
import com.example.demo.model.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public AuthService(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    public Optional<User> authenticate(String username, String rawPassword) {
        // Check Admin first
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            if (admin.getPassword() != null && admin.getPassword().equals(rawPassword)) {
                // Map to generic User DTO
                return Optional.of(new User(admin.getUsername(), "admin@system", admin.getPassword(),
                        admin.isRole() ? "ADMIN" : "USER"));
                // Note: 'Quyen' bit 0/1. If 1 is admin.
            }
        }

        // Check Customer
        Optional<Customer> customerOpt = customerRepository.findByUsername(username);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            if (customer.getPassword() != null && customer.getPassword().equals(rawPassword)) {
                return Optional
                        .of(new User(customer.getUsername(), customer.getEmail(), customer.getPassword(), "USER"));
            }
        }

        return Optional.empty();
    }

    public boolean register(String username, String email, String password, String fullName, String address,
            String phoneNumber) {
        if (customerRepository.findByUsername(username).isPresent()) {
            return false;
        }
        Customer newCustomer = new Customer();
        newCustomer.setUsername(username);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);

        // fields
        newCustomer.setFullName(fullName);
        newCustomer.setAddress(address);
        newCustomer.setPhoneNumber(phoneNumber);

        // Manually generate ID
        Long maxId = customerRepository.findMaxId();
        newCustomer.setId((maxId == null) ? 1L : maxId + 1);

        customerRepository.save(newCustomer);
        return true;
    }

    public Optional<User> findByUsername(String username) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            return Optional.of(new User(admin.get().getUsername(), "admin@system", admin.get().getPassword(), "ADMIN"));
        }
        return customerRepository.findByUsername(username)
                .map(c -> new User(c.getUsername(), c.getEmail(), c.getPassword(), "USER"));
    }
}
