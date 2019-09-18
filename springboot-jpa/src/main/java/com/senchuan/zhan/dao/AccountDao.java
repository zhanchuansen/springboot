package com.senchuan.zhan.dao;

import com.senchuan.zhan.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Integer> {
}
