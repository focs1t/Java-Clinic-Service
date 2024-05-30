package Dao;

import Controllers.MainApplication;
import Models.Appeals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Класс для доступа к данным об обращениях.
 */
public class AppealsDao implements Dao<Appeals, Integer> {
    private final Logger logger = LoggerFactory.getLogger(AppealsDao.class);
    static Properties property = new Properties();
    {
        try (InputStream input = this.getClass().getResourceAsStream("/statements.properties")) {
            property.load(input);
            logger.debug("Данные из файла statements.properties получены");
        } catch (IOException e) {
            logger.error("Ошибка получения данных из файла statements.properties", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Возвращает объект обращений по его идентификатору.
     * @param integer идентификатор обращений
     * @return объект обращений
     */
    @Override
    public Appeals findById(Integer integer) {
        Appeals appeals = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectAppealById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    appeals = new Appeals();
                    appeals.setId(resultSet.getInt("id"));
                    appeals.setData(resultSet.getDate("data"));
                    appeals.setPatientId(resultSet.getInt("patientId"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return appeals;
    }

    /**
     * Возвращает коллекцию всех обращений.
     * @return коллекция объектов обращений
     */
    @Override
    public Collection<Appeals> finAll() {
        logger.debug("Загрузка коллекции");
        List<Appeals> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_Appeal"))){
            rs = statement.executeQuery();
            list = mapper(rs);
            logger.debug("Коллекция успешно загружена");
        }
        catch (SQLException e){
            logger.error("Ошибка загрузки коллекции", e);
            System.out.println(e.getMessage());
        }
        return list;
    }

    /**
     * Сохраняет объект обращения в базу данных.
     * @param entity объект обращения
     * @return сохраненный объект обращения
     */
    @Override
    public Appeals save(Appeals entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertAppeal"),new String[] {"id"})) {
            statement.setDate(1, (Date) entity.getData());
            statement.setInt(2, entity.getPatientId());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                        return entity;
                    } else {
                        throw new SQLException("Unable to create, id was not found.");
                    }
                }
            }
            logger.debug("Объект успешно сохранен");
        } catch (SQLException e) {
            logger.error("Ошибка сохранения объекта", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Обновляет информацию об обращении.
     * @param entity объект обращения
     * @return обновленный объект обращения
     */
    @Override
    public Appeals update(Appeals entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateAppeal"))) {
            statement.setDate(1, (Date) entity.getData());
            statement.setInt(2, entity.getPatientId());
            statement.setInt(3, entity.getId());
            statement.executeUpdate();
            logger.debug("Объект успешно обновлен");
        } catch (SQLException e) {
            logger.error("Ошибка обновления объекта", e);
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Удаляет объект обращения.
     * @param entity объект обращения
     * @return удаленный объект обращения
     */
    @Override
    public Appeals delete(Appeals entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteAppeal")))
        {
            statement.setDate(1, (Date) entity.getData());
            statement.setInt(2, entity.getPatientId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Unable to delete.");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                entity.setId(generatedKeys.getInt(1));
            }
            logger.debug("Объект успешно удален");
            return entity;
        } catch (SQLException e) {
            logger.error("Ошибка удаления объекта", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Удаляет объект обращения по его идентификатору.
     * @param integer идентификатор обращения
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM appeals WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов обращения на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов обращения
     */
    protected  List<Appeals> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Appeals> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new Appeals(rs.getInt("id"),
                        rs.getDate("data"),
                        rs.getInt("patientId")
                ));
            }
            logger.debug("Список объектов успешно создан");
        }
        catch (SQLException e){
            logger.error("Ошибка создания списка объектов", e);
            System.out.println(e.getMessage());
        }
        return list;
    }
}
