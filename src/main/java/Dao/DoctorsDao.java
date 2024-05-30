package Dao;

import Controllers.MainApplication;
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
 * Класс для доступа к данным о врачах.
 */
public class DoctorsDao implements Dao<Doctors, Integer> {
    private final Logger logger = LoggerFactory.getLogger(DoctorsDao.class);
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
     * Возвращает объект врача по его идентификатору.
     * @param integer идентификатор врача
     * @return объект врача
     */
    @Override
    public Doctors findById(Integer integer) {
        Doctors doctors = null;
        logger.debug("Поиск по ID");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.selectDoctorById"))) {
            statement.setLong(1, integer);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    doctors = new Doctors();
                    doctors.setId(resultSet.getInt("id"));
                    doctors.setLastName(resultSet.getString("lastName"));
                    doctors.setFirstName(resultSet.getString("firstName"));
                    doctors.setMiddleName(resultSet.getString("middleName"));
                    doctors.setDoctorSpecialtyId(resultSet.getInt("doctorSpecialtyId"));
                    doctors.setDoctorCategoryId(resultSet.getInt("doctorCategoryId"));
                }
            }
            logger.debug("Объект успешно получен");
        } catch (SQLException e) {
            logger.error("Ошибка поиска по ID", e);
            throw new RuntimeException(e);
        }
        return doctors;
    }

    /**
     * Возвращает коллекцию всех врачах.
     * @return коллекция объектов врачей
     */
    @Override
    public Collection<Doctors> finAll() {
        logger.debug("Загрузка коллекции");
        List<Doctors> list = null;
        ResultSet rs = null;
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.find_all_Doctor"))){
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
     * Сохраняет объект врача в базу данных.
     * @param entity объект врача
     * @return сохраненный объект врача
     */
    @Override
    public Doctors save(Doctors entity) {
        logger.debug("Сохранение объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.insertDoctor"),new String[] {"id"})) {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getDoctorSpecialtyId());
            statement.setInt(5, entity.getDoctorCategoryId());
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
     * Обновляет информацию о враче.
     * @param entity объект врача
     * @return обновленный объект врача
     */
    @Override
    public Doctors update(Doctors entity) {
        logger.debug("Обновление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.updateDoctor"))) {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getDoctorSpecialtyId());
            statement.setInt(5, entity.getDoctorCategoryId());
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
            logger.debug("Объект успешно обновлен");
        } catch (SQLException e) {
            logger.error("Ошибка обновления объекта", e);
            System.out.println(e.getMessage());
        }
        return entity;
    }

    /**
     * Удаляет объект врача.
     * @param entity объект врача
     * @return удаленный объект врача
     */
    @Override
    public Doctors delete(Doctors entity) {
        logger.debug("Удаление объекта");
        try (PreparedStatement statement = MainApplication.getConnection().prepareStatement(property.getProperty("sql.deleteDoctor")))
        {
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getMiddleName());
            statement.setInt(4, entity.getDoctorSpecialtyId());
            statement.setInt(5, entity.getDoctorCategoryId());
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
     * Удаляет объект врача по его идентификатору.
     * @param integer идентификатор врача
     */
    @Override
    public void deleteById(Integer integer) {
        logger.debug("Удаление объекта");
        try(PreparedStatement statement = MainApplication.getConnection().prepareStatement("DELETE FROM doctors WHERE id = ?")){
            statement.setInt(1, integer);
            statement.executeUpdate();
            logger.debug("Объект успешно удален");
        }catch (SQLException e){
            logger.error("Ошибка удаления объекта", e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Создает список объектов врача на основе результата запроса к базе данных.
     * @param rs результат запроса к базе данных
     * @return список объектов врача
     */
    protected  List<Doctors> mapper (ResultSet rs){
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
}
