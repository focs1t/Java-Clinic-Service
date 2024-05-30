package Dao;

import Controllers.MainApplication;
import Models.Patients;
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
 * Класс для доступа к данным о пациентах.
 */
public class PatientsDao implements Dao<Patients, Integer> {
    private final Logger logger = LoggerFactory.getLogger(PatientsDao.class);
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
     * Возвращает объект пациента по его идентификатору.
     * @param integer идентификатор пациента
     * @return объект пациента
     */
    @Override
    public Patients findById(Integer integer) {
        Patients patients = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectPatientById"))) {
            statement.setInt(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    patients = new Patients();
                    patients.setId(resultSet.getInt("id"));
                    patients.setLastName(resultSet.getString("lastName"));
                    patients.setFirstName(resultSet.getString("firstName"));
                    patients.setMiddleName(resultSet.getString("middleName"));
                    patients.setAge(resultSet.getInt("age"));
                    patients.setAddress(resultSet.getString("address"));
                    patients.setPatientCategoryId(resultSet.getInt("patientCategoryId"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return patients;
    }

    /**
     * Возвращает коллекцию всех пациентов.
     * @return коллекция объектов пациентов
     */
    @Override
    public Collection<Patients> finAll() {
        logger.debug("Загрузка коллекции");
        List<Patients> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_Patient"))){
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
     * Сохраняет объект пациента в базу данных.
     * @param entity объект пациента
     * @return сохраненный объект пациента
     */
    @Override
    public Patients save(Patients entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertPatient"),new String[] {"id"})) {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getAge());
            statement.setString(5, entity.getAddress());
            statement.setInt(6, entity.getPatientCategoryId());
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
     * Обновляет информацию о пациенте.
     * @param entity объект пациента
     * @return обновленный объект пациента
     */
    @Override
    public Patients update(Patients entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updatePatient"))) {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getAge());
            statement.setString(5, entity.getAddress());
            statement.setInt(6, entity.getPatientCategoryId());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
            logger.debug("Объект успешно обновлен");
        } catch (SQLException e) {
            logger.error("Ошибка обновления объекта", e);
            System.out.println(e.getMessage());
            //
        }
        return entity;
    }

    /**
     * Удаляет объект пациента.
     * @param entity объект пациента
     * @return удаленный объект пациента
     */
    @Override
    public Patients delete(Patients entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deletePatient")))
        {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getAge());
            statement.setString(5, entity.getAddress());
            statement.setInt(6, entity.getPatientCategoryId());
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
     * Удаляет объект пациента по его идентификатору.
     * @param integer идентификатор пациента
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM patients WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов пациента на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов пациента
     */
    protected  List<Patients> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Patients> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new Patients(rs.getInt("id"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("middleName"),
                        rs.getInt("age"),
                        rs.getString("address"),
                        rs.getInt("patientCategoryId")
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
