package Service;

import Dao.TreatmentsDao;
import Models.Treatments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с лечением пациентов.
 */
public class ServiceTreatments {
    private final Logger logger = LoggerFactory.getLogger(ServiceTreatments.class);
    private  final TreatmentsDao dao;

    /**
     * Конструктор класса ServiceTreatments.
     * @param dao Объект класса TreatmentsDao для доступа к данным о лечении.
     */
    public ServiceTreatments(TreatmentsDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о лечении пациента.
     * @param treatments Объект класса Treatments с информацией о лечении.
     * @param appealId Уникальный идентификатор обращения пациента.
     * @param doctorId Уникальный идентификатор врача.
     * @param diagnosisId Уникальный идентификатор диагноза.
     * @param price Цена лечения.
     */
    public void updateTreatmnets(Treatments treatments, int appealId, int doctorId, int diagnosisId, int price){
        treatments.setAppealId(appealId);
        treatments.setDoctorId(doctorId);
        treatments.setDiagnosisId(diagnosisId);
        treatments.setPrice(price);
        dao.update(treatments);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о лечении пациента.
     * @param treatments Объект класса Treatments с информацией о лечении.
     */
    public void deleteTreatmnets(Treatments treatments){
        dao.deleteById(treatments.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех лечениях пациентов.
     */
    public Collection<Treatments> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания нового лечения пациента.
     * @param appealId Уникальный идентификатор обращения пациента.
     * @param doctorId Уникальный идентификатор врача.
     * @param diagnosisId Уникальный идентификатор диагноза.
     * @param price Цена лечения.
     * @return Объект класса Treatments с информацией о новом лечении.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public Treatments createTreatmnets(int appealId, int doctorId, int diagnosisId, int price){
        if(appealId <= 0 || doctorId <= 0 || diagnosisId <= 0 || price <= 0){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        Treatments treatments = new Treatments(0, appealId, doctorId, diagnosisId, price);
        dao.save(treatments);
        logger.info("Добавление");
        return treatments;
    }
}

