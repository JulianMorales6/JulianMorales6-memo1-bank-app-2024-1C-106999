package com.aninfo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.aninfo.model.Account;

@RepositoryRestResource
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountByCbu(Long cbu);

    @Override
    List<Account> findAll();

}
