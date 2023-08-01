package com.luv2code.springboot.demosecurity.dao;

import com.luv2code.springboot.demosecurity.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl implements UserDao{

    private EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager theEntityManager){
        this.entityManager = theEntityManager;
    }

    @Override
    public User findByName(String userName) {
        TypedQuery<User> theQuery = entityManager.createQuery("FROM User WHERE userName=:uName",User.class);
        theQuery.setParameter("uName",userName);
        User theUser = null;

        try{
            theUser = theQuery.getSingleResult();
        }catch (Exception e){
            theUser = null;
        }

        return theUser;
    }

    @Override
    @Transactional
    public void save(User theUser) {
        // create the user ... finally
        entityManager.merge(theUser);
    }
}
