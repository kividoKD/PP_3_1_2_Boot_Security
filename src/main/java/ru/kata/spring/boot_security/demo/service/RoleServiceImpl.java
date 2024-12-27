package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        roleDao.save(role);
    }

    @Override
    public List<Role> getListRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role getRole(Long id) {
        return roleDao.getById(id);
    }

    @Transactional
    @Override
    public void updateRole(Long id, Role role) {
        role.setId(id);
        roleDao.saveAndFlush(role);
    }

    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleDao.deleteById(id);
    }
}
