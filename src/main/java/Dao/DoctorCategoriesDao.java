package Dao;

import Controllers.MainApplication;
import Models.DoctorCategories;
import Models.Doctors;
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
 * Класс для доступа к данным о категории врачей.
 */
public class DoctorCategoriesDao implements Dao<DoctorCategories, Integer> {
    private final Logger logger = LoggerFactory.getLogger(DoctorCategoriesDao.class);
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
     * Возвращает объект категории врача по его идентификатору.
     * @param integer идентификатор категории врача
     * @return объект категории врача
     */
    @Override
    public DoctorCategories findById(Integer integer) {
        DoctorCategories doctorCategories = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectDoctorCategoryById"))) {
            statement.setInt(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    doctorCategories = new DoctorCategories();
                    doctorCategories.setId( resultSet.getInt("id"));
                    doctorCategories.setName(resultSet.getString("name"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return doctorCategories;
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
        String query = "SELECT * FROM doctors WHERE doctorCategoryId = ?";
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
     * Возвращает коллекцию всех категорий врачей.
     * @return коллекция объектов категорий врачей
     */
    @Override
    public Collection<DoctorCategories> finAll() {
        logger.debug("Загрузка коллекции");
        List<DoctorCategories> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_DoctorCategory"))){
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
     * Сохраняет объект категории врача в базу данных.
     * @param entity объект категории врача
     * @return сохраненный объект категории врача
     */
    @Override
    public DoctorCategories save(DoctorCategories entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertDoctorCategory"),new String[] {"id"})) {
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
     * Обновляет информацию о категории врача.
     * @param entity объект категории врача
     * @return обновленный объект категории врача
     */
    @Override
    public DoctorCategories update(DoctorCategories entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateDoctorCategory"))) {
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
     * Удаляет объект категории врача.
     * @param entity объект категории врача
     * @return удаленный объект категории врача
     */
    @Override
    public DoctorCategories delete(DoctorCategories entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteDoctorCategory")))
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
     * Удаляет объект категории врача по его идентификатору.
     * @param integer идентификатор категории врача
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM doctorCategories WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов категории врача на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов категории врача
     */
    protected  List<DoctorCategories> mapper (ResultSet rs){
        logger.debug("Создание списка объектов");
        List<DoctorCategories> list = new ArrayList<>();
        try{
            while (rs.next()) {
                list.add(new DoctorCategories(rs.getInt("id"),
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
