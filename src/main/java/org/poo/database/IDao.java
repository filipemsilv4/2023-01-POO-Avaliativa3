package org.poo.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public interface IDao<T> {

    // Save
    public String getSaveStatement();

    public String getUpdateStatement();

    public void composeSaveOrUpdateStatement(PreparedStatement pstmt, T e);

    public Long saveOrUpdate(T e);

    // Get by ID
    public String getFindByIdStatement();

    public T findById(Long id);

    // Get all
    public String getFindAllStatement();

    public List<T> findAll();

    // Assembly objects
    public T extractObject(ResultSet resultSet);

    public List<T> extractObjects(ResultSet resultSet);

}
