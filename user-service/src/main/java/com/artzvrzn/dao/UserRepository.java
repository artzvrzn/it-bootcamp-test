package com.artzvrzn.dao;

import com.artzvrzn.domain.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface UserRepository {

  User save(User user);

  List<User> findAll(Sort sort);

  Page<User> findAll(Pageable pageable);
}
