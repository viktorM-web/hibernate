package org.example;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.example.converter.BirthdayConverter;
import org.example.entity.Birthday;
import org.example.entity.Role;
import org.example.entity.UserEntity;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.BlockingQueue;

public class HibernateRunner {

    public static void main(String[] args) throws SQLException {

        UserEntity user = UserEntity.builder()
                .username("ivan@gmail.com")
                .lastname("Ivanov")
                .firstname("Ivan")
                .build();

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                session1.beginTransaction();

                session1.saveOrUpdate(user);

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();

                user.setFirstname("Sveta");

//                session2.refresh(user);
//
////                UserEntity freshUser = session2.get(UserEntity.class, user.getUsername());
////                user.setFirstname(freshUser.getFirstname());
////                user.setLastname(freshUser.getLastname());
//
////                **how work refresh**

                Object mergeUser = session2.merge(user);

//                UserEntity freshUser = session2.get(UserEntity.class, user.getUsername());
//                freshUser.setFirstname(user.getFirstname());
//                freshUser.setLastname(user.getLastname());

//                **how work merge**


                session2.getTransaction().commit();
            }
        }
    }
}
