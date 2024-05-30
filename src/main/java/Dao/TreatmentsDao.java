package Dao;

import Controllers.MainApplication;
import Models.Treatments;
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
 * Класс для доступа к данным о лечениях.
 */
public class TreatmentsDao implements Dao<Treatments, Integer> {
    private final Logger logger = LoggerFactory.getLogger(TreatmentsDao.class);
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
     * Возвращает объект лечения по его идентификатору.
     * @param integer идентификатор лечения
     * @return объект лечения
     */
    @Override
    public Treatments findById(Integer integer) {
        Treatments treatments = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectTreatmentById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    treatments = new Treatments();
                    treatments.setId(resultSet.getInt("id"));
                    treatments.setAppealId(resultSet.getInt("AppealId"));
                    treatments.setDoctorId(resultSet.getInt("DoctorId"));
                    treatments.setDiagnosisId(resultSet.getInt("DiagnosisId"));
                    treatments.setPrice(resultSet.getInt("price"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return treatments;
    }

    /**
     * Возвращает коллекцию всех лечений.
     * @return коллекция объектов лечений
     */
    @Override
    public Collection<Treatments> finAll() {
        logger.debug("Загрузка коллекции");
        List<Treatments> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_Treatment"))){
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
     * Сохраняет объект лечения в базу данных.
     * @param entity объект лечения
     * @return сохраненный объект лечения
     */
    @Override
    public Treatments save(Treatments entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertTreatment"),new String[] {"id"})) {
            statement.setInt(1, entity.getAppealId());
            statement.setInt(2, entity.getDoctorId());
            statement.setInt(3, entity.getDiagnosisId());
            statement.setInt(4, entity.getPrice());
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
     * Обновляет информацию о лечении.
     * @param entity объект лечения
     * @return обновленный объект лечения
     */
    @Override
    public Treatments update(Treatments entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateTreatment"))) {
            statement.setInt(1, entity.getAppealId());
            statement.setInt(2, entity.getDoctorId());
            statement.setInt(3, entity.getDiagnosisId());
            statement.setInt(4, entity.getPrice());
            statement.setInt(5, entity.getId());
            statement.executeUpdate();
            logger.debug("Объект успешно обновлен");
        } catch (SQLException e) {
            logger.error("Ошибка обновления объекта", e);
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Удаляет объект лечения.
     * @param entity объект лечения
     * @return удаленный объект лечения
     */
    @Override
    public Treatments delete(Treatments entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteTreatment")))
        {
            statement.setInt(1, entity.getAppealId());
            statement.setInt(2, entity.getDoctorId());
            statement.setInt(3, entity.getDiagnosisId());
            statement.setInt(4, entity.getPrice());
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
     * Удаляет объект лечения по его идентификатору.
     * @param integer идентификатор лечения
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM treatments WHERE id = ?")){
            statement.setLong(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов лечения на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов лечения
     */
    protected  List<Treatments> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Treatments> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new Treatments(rs.getInt("id"),
                        rs.getInt("AppealId"),
                        rs.getInt("DoctorId"),
                        rs.getInt("DiagnosisId"),
                        rs.getInt("price")
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
