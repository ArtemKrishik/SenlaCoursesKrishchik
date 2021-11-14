package com.github.krishchik.whowithme.repository;

import com.github.krishchik.whowithme.api.repository.UserRepository;
import com.github.krishchik.whowithme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final String INSERT_INTO_USERS_ID_LOGIN_PASSWORD_VALUES = "insert into users (id,login,password) values ( ?, ?, ?)";
    private static final String DELETE_FROM_USERS = "delete from users where id = ?";
    private static final String SELECT_ALL_FROM_USERS = "SELECT * FROM users";
    private static final String SELECT_ALL_FROM_USERS_BY_ID = "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_BY_ID = "update users set \"login\" = ?,password = ? where id = ?";

    @Autowired
    private final Connection connection;

    public JdbcUserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getById(Long id) throws Exception {
        try(final PreparedStatement statement = connection.
                prepareStatement(SELECT_ALL_FROM_USERS_BY_ID)){
            statement.setLong(1,id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

    @Override
    public void save(User user) throws Exception {
        try(final PreparedStatement statement = connection.
                prepareStatement(INSERT_INTO_USERS_ID_LOGIN_PASSWORD_VALUES)){
            statement.setLong(1,user.getId());
            statement.setString(2,user.getLogin());
            statement.setString(3,user.getPassword());
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        try(final PreparedStatement statement = connection.
                prepareStatement(DELETE_FROM_USERS)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<User> getAll() throws Exception{
        try(final PreparedStatement statement = connection.
                prepareStatement(SELECT_ALL_FROM_USERS)){
            ArrayList<User> users = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        }
    }



    @Override
    public void update(User user) throws Exception {
        try(final PreparedStatement statement = connection.
                prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setLong(3, user.getId());
            statement.executeUpdate();
        }
    }



}
