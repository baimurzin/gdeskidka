package com.baimurzin.gs.service;

import com.baimurzin.gs.model.Role;
import com.baimurzin.gs.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role save(Role role) {
        Role byName = roleRepository.findByName(role.getName());
        return  (byName == null) ? roleRepository.save(role) : byName;
    }
}
