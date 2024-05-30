package Service;

import Dao.DoctorCategoriesDao;
import Models.DoctorCategories;
import Models.Doctors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о категории врачей.
 */
public class ServiceDoctorCategories {
    private final Logger logger = LoggerFactory.getLogger(ServiceDoctorCategories.class);
    private  final DoctorCategoriesDao dao;

    /**
     * Конструктор класса ServiceDoctorCategories.
     * @param dao Объект класса DoctorCategoriesDao для доступа к данным о категориях врачей.
     */
    public ServiceDoctorCategories(DoctorCategoriesDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о категории врача.
     * @param doctorCategories Объект класса DoctorCategories с информацией о категории врача.
     * @param name Наименование категории врача.
     */
    public void updateDoctorCategories(DoctorCategories doctorCategories, String name){
        doctorCategories.setName(name);
        dao.update(doctorCategories);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о категории врача.
     * @param doctorCategories Объект класса DoctorCategories с информацией о категории врача.
     */
    public void deleteDoctorCategories(DoctorCategories doctorCategories){
        dao.deleteById(doctorCategories.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех категориях врачей.
     */
    public Collection<DoctorCategories> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания новой категории врача.
     * @param name Наименование новой категории врача.
     * @return Объект класса DoctorCategories с информацией о новой категории врача.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public DoctorCategories createDoctorCategories(String name){
        if(name == null){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        DoctorCategories doctorCategories = new DoctorCategories(0, name);
        dao.save(doctorCategories);
        logger.info("Добавление");
        return doctorCategories;
    }

    /**
     * Метод для поиска врачей по идентификатору категории.
     * @param doctorId Уникальный идентификатор врача.
     * @return Объект класса Doctors соответствующего врача.
     * @throws SQLException если возникает ошибка при работе с базой данных.
     */
    public Collection<Doctors> FindByDoctorId(int doctorId) throws SQLException {
        logger.info("Поиск");
        return dao.findByDoctorId(doctorId);
    }
}
