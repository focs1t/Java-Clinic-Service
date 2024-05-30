package Service;


import Dao.PatientCategoriesDao;
import Models.PatientCategories;
import Models.Patients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о категориях пациентов.
 */
public class ServicePatientCategories {
    private final Logger logger = LoggerFactory.getLogger(ServicePatientCategories.class);
    private  final PatientCategoriesDao dao;

    /**
     * Конструктор класса ServicePatientCategories.
     * @param dao Объект класса PatientCategoriesDao для доступа к данным о категориях пациентов.
     */
    public ServicePatientCategories(PatientCategoriesDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о категории пациента.
     * @param patientCategories Объект класса PatientCategories с информацией о категории пациента.
     * @param name Наименование категории пациента.
     * @param discount Скидка на услуги для данной категории пациента.
     */
    public void updatePatientCategories(PatientCategories patientCategories, String name, int discount){
        patientCategories.setName(name);
        patientCategories.setDiscount(discount);
        dao.update(patientCategories);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о категории пациента.
     * @param patientCategories Объект класса PatientCategories с информацией о категории пациента.
     */
    public void deletePatientCategories(PatientCategories patientCategories){
        dao.deleteById(patientCategories.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех категориях пациентов.
     */
    public Collection<PatientCategories> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания новой категории пациента.
     * @param name Наименование новой категории пациента.
     * @param discount Скидка на услуги для данной категории пациента.
     * @return Объект класса PatientCategories с информацией о новой категории пациента.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public PatientCategories createPatientCategories(String name, int discount){
        if(name == null || discount <= 0){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        PatientCategories patientCategories = new PatientCategories(0, name, discount);
        dao.save(patientCategories);
        logger.info("Добавление");
        return patientCategories;
    }

    /**
     * Метод для поиска пациентов по идентификатору категории пациента.
     * @param patientId Уникальный идентификатор пациента.
     * @return Объект класса Patients соответствующего пациента.
     * @throws SQLException если возникает ошибка при работе с базой данных.
     */
    public Collection<Patients> FindByPatientId(int patientId) throws SQLException {
        logger.info("Поиск");
        return dao.findByPatientId(patientId);
    }
}
