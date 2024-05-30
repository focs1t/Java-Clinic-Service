package Dao;

import Controllers.MainApplication;
import Models.PatientCategories;
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
 * Класс для доступа к данным о категории пациентов.
 */
public class PatientCategoriesDao implements Dao<PatientCategories, Integer> {
    private final Logger logger = LoggerFactory.getLogger(PatientCategoriesDao.class);
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
     * Возвращает объект категории пациента по его идентификатору.
     * @param integer идентификатор категории пациента
     * @return объект категории пациента
     */
    @Override
    public PatientCategories findById(Integer integer) {
        PatientCategories patientCategories = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectPatientCategoryById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    patientCategories = new PatientCategories();
                    patientCategories.setId(resultSet.getInt("id"));
                    patientCategories.setName(resultSet.getString("name"));
                    patientCategories.setDiscount(resultSet.getInt("discount"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return patientCategories;
    }

    /**
     * Возвращает объект пациента по его идентификатору.
     * @param id идентификатор пациента
     * @return объект пациента
     */
    public Collection<Patients> findByPatientId(int id) throws SQLException {
        List<Patients> list1 = null;
        ResultSet rs = null;
        logger.debug("Поиск по ID пациентов");
        String query = "SELECT * FROM patients WHERE patientCategoryId = ?";
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
    protected  List<Patients> mapper1 (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<Patients> list1 = new ArrayList<>();
        try{
            while (rs.next()) {
                list1.add(new Patients(rs.getInt("id"),
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
        return list1;
    }

    /**
     * Возвращает коллекцию всех категорий пациентов.
     * @return коллекция объектов категорий пациентов
     */
    @Override
    public Collection<PatientCategories> finAll() {
        logger.debug("Загрузка коллекции");
        List<PatientCategories> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_PatientCategory"))){
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
     * Сохраняет объект категории пациента в базу данных.
     * @param entity объект категории пациента
     * @return сохраненный объект категории пациента
     */
    @Override
    public PatientCategories save(PatientCategories entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertPatientCategory"),new String[] {"id"})) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getDiscount());
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
     * Обновляет информацию о категории пациента.
     * @param entity объект категории пациента
     * @return обновленный объект категории пациента
     */
    @Override
    public PatientCategories update(PatientCategories entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updatePatientCategory"))) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getDiscount());
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
     * Удаляет объект категории пациента.
     * @param entity объект категории пациента
     * @return удаленный объект категории пациента
     */
    @Override
    public PatientCategories delete(PatientCategories entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deletePatientCategory")))
        {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getDiscount());
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
     * Удаляет объект категории пациента по его идентификатору.
     * @param integer идентификатор категории пациента
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM patientCategories WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов категории пациента на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов категории пациента
     */
    protected  List<PatientCategories> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<PatientCategories> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new PatientCategories(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("discount")
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
