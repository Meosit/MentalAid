package by.mksn.epam.mentalaid.dao;

import by.mksn.epam.mentalaid.dao.exception.DAOException;
import by.mksn.epam.mentalaid.entity.Question;

import java.util.List;

public interface QuestionDAO {

    Question insert(Question entity) throws DAOException;

    List<Question> selectAll() throws DAOException;

    List<Question> selectByUsername(String username) throws DAOException;

    void update(Question updatedEntity) throws DAOException;

    void delete(long id) throws DAOException;

}
