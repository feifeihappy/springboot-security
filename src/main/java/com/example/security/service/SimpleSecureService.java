package com.example.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class SimpleSecureService {

    @Secured("ROLE_USER")
    public String secure() {
        return "Hello User Security";
    }

    @PreAuthorize("hasRole('ADMIN')")
    public String authorized() {
        return "Hello Admin Security";
    }

}
