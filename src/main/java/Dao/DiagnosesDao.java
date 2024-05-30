package Dao;

import Controllers.MainApplication;
import Models.Diagnoses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Класс для доступа к данным о диагнозах.
 */
public class DiagnosesDao implements Dao<Diagnoses, Integer> {
    private final Logger logger = LoggerFactory.getLogger(DiagnosesDao.class);
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
     * Возвращает объект диагноза по его идентификатору.
     * @param integer идентификатор диагноза
     * @return объект диагноза
     */
    @Override
    public Diagnoses findById(Integer integer) {
        Diagnoses diagnoses = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectDiagnosisById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    diagnoses = new Diagnoses();
                    diagnoses.setId(resultSet.getInt("id"));
                    diagnoses.setName(resultSet.getString("name"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return diagnoses;
    }

    /**
     * Возвращает коллекцию всех диагнозах.
     * @return коллекция объектов диагнозах
     */
    @Override
    public Collection<Diagnoses> finAll() {
        logger.debug("Загрузка коллекции");
        List<Diagnoses> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_Diagnosis"))){
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
     * Сохраняет объект диагноза в базу данных.
     * @param entity объект диагноза
     * @return сохраненный объект диагноза
     */
    @Override
    public Diagnoses save(Diagnoses entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertDiagnosis"),new String[] {"id"})) {
            statement.setString(1, entity.getName());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted == 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId((int) generatedKeys.getInt(1));
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
     * Обновляет информацию о диагнозе.
     * @param entity объект диагонза
     * @return обновленный объект диагноза
     */
    @Override
    public Diagnoses update(Diagnoses entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateDiagnosis"))) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();
            logger.debug("Объект успешно обновлен");
        } catch (SQLException e) {
            logger.error("Ошибка обновления объекта", e);
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Удаляет объект диагноза.
     * @param entity объект диагноза
     * @return удаленный объект диагноза
     */
    @Override
    public Diagnoses delete(Diagnoses entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteDiagnosis")))
        {
            statement.setString(1, entity.getName());
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
     * Удаляет объект диагноза по его идентификатору.
     * @param integer идентификатор диагноза
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM diagnoses WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов диагноза на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов диагноза
     */
    protected  List<Diagnoses> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Diagnoses> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new Diagnoses(rs.getInt("id"),
                        rs.getString("name")
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
