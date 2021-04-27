package com.ftn.osa.service.impl;

import com.ftn.osa.repository.AdminRepository;
import com.ftn.osa.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

}
