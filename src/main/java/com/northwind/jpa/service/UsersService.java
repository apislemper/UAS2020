/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.northwind.jpa.service;

import com.northwind.jpa.entity.Users;
import com.northwind.jpa.repository.UsersRepository;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author Hafish
 */

@Service
public class UsersService implements RepoService<Products>{

    @Resource
    private UsersRepository repo;
    
    @Override
    public Users create(Users obj) {
        Users prod = repo.save(obj);
        return prod;
    }

    @Override
    public Users update(Users obj) {
        Users prod = repo.findById(obj.getUsersID()).orElse(null);
        if (prod != null) {
            prod.setName(obj.getName());
            prod.setTitle(obj.getTitle());
            prod.setBirthDate(obj.getBirthDate());
            prod.setHireDate(obj.getHireDate());
            prod.setPosition(obj.getPosition());
            prod.setEmail(obj.getEmail());
            prod.setPassword(obj.getPassword());
            repo.flush();
            return prod;
        }else{
            throw new NoSuchElementException("Data Not Found");
        }
    }

    @Override
    public Users delete(Object id) {
        Users prod = getById(id);
        if (prod != null) {
                repo.delete(prod);
                return prod;
        }else{
            throw new NoSuchElementException("Data Not Found");
        }
    }

    @Override
    public Users getById(Object id) {
        return repo.findById(Integer.valueOf(id.toString())).orElse(null);
    }

    @Override
    public List<Users> getAll() {
        return repo.findAll();
    }
    
    public Page<Users> getByPage(int page, int pageSize, String Name){
        Sort sortBy = Sort.by(sort);
        sortBy = asc ? sortBy.ascending() : sortBy.descending();
        Pageable pageable = PageRequest.of(page, pageSize, sortBy); 
        return  repo.findByUsersNameLike(Name, pageable);
    }
            
}
