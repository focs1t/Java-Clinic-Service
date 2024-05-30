package Dao;

import Controllers.MainApplication;
import Models.DoctorSpecialties;
import Models.Doctors;
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
 * Класс для доступа к данным о специальности врачей.
 */
public class DoctorSpecialtiesDao implements Dao<DoctorSpecialties, Integer> {
    private final Logger logger = LoggerFactory.getLogger(DoctorSpecialtiesDao.class);
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
     * Возвращает объект специальности врача по его идентификатору.
     * @param integer идентификатор специальности врача
     * @return объект специальности врача
     */
    @Override
    public DoctorSpecialties findById(Integer integer) {
        DoctorSpecialties doctorSpecialties = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectDoctorSpecialtyById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    doctorSpecialties = new DoctorSpecialties();
                    doctorSpecialties.setId(resultSet.getInt("id"));
                    doctorSpecialties.setName(resultSet.getString("name"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return doctorSpecialties;
    }

    /**
     * Возвращает объект врача по его идентификатору.
     * @param id идентификатор врача
     * @return объект врача
     */
    public Collection<Doctors> findByDoctorId(int id) throws SQLException {
        List<Doctors> list1 = null;
        ResultSet rs = null;
        logger.debug("Поиск по ID пациентов");
        String query = "SELECT * FROM doctors WHERE doctorSpecialtyId = ?";
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(query)) {
            statement.setInt(1, id);
            rs = statement.executeQuery();
            list1 = mapper1(rs);
            logger.debug("Коллекция успешно загружена");
            logger.debug("Объект пациентов успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID пациента", e);
            throw new RuntimeException(e);
        }
        return list1;
    }

    /**
     * Создает список объектов пациента на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов пациента
     */
    protected  List<Doctors> mapper1 (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Doctors> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new Doctors(rs.getInt("id"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("middleName"),
                        rs.getInt("doctorSpecialtyId"),
                        rs.getInt("doctorCategoryId")
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

    /**
     * Возвращает коллекцию всех специальностей врачей.
     * @return коллекция объектов специальностей врачей
     */
    @Override
    public Collection<DoctorSpecialties> finAll() {
        logger.debug("Загрузка коллекции");
        List<DoctorSpecialties> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_DoctorSpecialty"))){
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
     * Сохраняет объект специальности врача в базу данных.
     * @param entity объект специальности врача
     * @return сохраненный объект специальности врача
     */
    @Override
    public DoctorSpecialties save(DoctorSpecialties entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertDoctorSpecialty"),new String[] {"id"})) {
            statement.setString(1, entity.getName());
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
     * Обновляет информацию о специальности врача.
     * @param entity объект специальности врача
     * @return обновленный объект специальности врача
     */
    @Override
    public DoctorSpecialties update(DoctorSpecialties entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateDoctorSpecialty"))) {
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
     * Удаляет объект специальности врача.
     * @param entity объект специальности врача
     * @return удаленный объект специальности врача
     */
    @Override
    public DoctorSpecialties delete(DoctorSpecialties entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteDoctorSpecialty")))
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
     * Удаляет объект специальности врача по его идентификатору.
     * @param integer идентификатор специальности врача
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM doctorSpecialties WHERE id = ?")){
            statement.setLong(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов специальности врача на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов специальности врача
     */
    protected  List<DoctorSpecialties> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<DoctorSpecialties> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new DoctorSpecialties(rs.getInt("id"),
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
