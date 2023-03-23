package com.artzvrzn.dao.impl;

import com.artzvrzn.dao.UserRepository;
import com.artzvrzn.domain.User;
import com.artzvrzn.util.LogMessages;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
  private final EntityManagerFactory emf;

  @Override
  public User save(User user) {
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    try {
      transaction.begin();
      em.persist(user);
      transaction.commit();
      log.info(LogMessages.QUERY_ENTITY_CREATED, user.getId());
      return user;
    } catch (Exception exc) {
      transaction.rollback();
      throw exc;
    } finally {
      em.close();
    }
  }

  @Override
  public List<User> findAll(Sort sort) {
    final String sql = "select u from User u order by u.email";
    EntityManager em = emf.createEntityManager();
    try {
      TypedQuery<User> query = em.createQuery(sql, User.class);
      List<User> users = query.getResultList();
      log.info(LogMessages.QUERY_RESULT_AMOUNT, users.size());
      return users;
    } finally {
      em.close();
    }
  }

  @Override
  public Page<User> findAll(Pageable pageable) {
    final String count = "select count(u) from User u";
    final String select = "select u from User u order by u.email";
    EntityManager em = emf.createEntityManager();
    try {
      TypedQuery<Long> countQuery = em.createQuery(count, Long.class);
      long total = countQuery.getSingleResult();
      TypedQuery<User> selectQuery = em.createQuery(select, User.class)
        .setFirstResult((int) pageable.getOffset())
        .setMaxResults(pageable.getPageSize());
      List<User> users = selectQuery.getResultList();
      log.info(LogMessages.QUERY_RESULT_AMOUNT, users.size());
      return new PageImpl<>(users, pageable, total);
    } finally {
      em.close();
    }
  }
}
