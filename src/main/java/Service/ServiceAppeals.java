package Service;

import Dao.AppealsDao;
import Models.Appeals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Date;

/**
 * Класс, предоставляющий сервисы для работы с данными об обращениях.
 */
public class ServiceAppeals {
    private final Logger logger = LoggerFactory.getLogger(ServiceAppeals.class);
    private  final AppealsDao dao;

    /**
     * Конструктор класса ServiceAppeals.
     * @param dao Объект класса AppealsDao для доступа к данным об обращениях.
     */
    public ServiceAppeals(AppealsDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации об обращении.
     * @param appeals Объект класса Appeals с информацией об обращении.
     * @param data Дата обращения.
     * @param patientId Уникальный идентификатор пациента.
     */
    public void updateAppeals(Appeals appeals, Date data, int patientId){
        appeals.setData(data);
        appeals.setPatientId(patientId);
        dao.update(appeals);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации об обращении.
     * @param appeals Объект класса Appeals с информацией об обращении.
     */
    public void deleteAppeals(Appeals appeals){
        dao.deleteById(appeals.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех обращениях.
     */
    public Collection<Appeals> ViewField(){
        return dao.finAll();

    }

    /**
     * Метод для создания нового врача.
     * @param data Дата обращения.
     * @param patientId Уникальный идентификатор пациента.
     * @return Объект класса Appeals с информацией о новом обращении.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public Appeals createAppeals(Date data, int patientId){
        if(data == null || patientId <= 0){
            throw  new IllegalArgumentException("Wrong parameters");
        }

        Appeals appeals = new Appeals(0, data, patientId);
        dao.save(appeals);
        logger.info("Добавление");
        return appeals;
    }
}
