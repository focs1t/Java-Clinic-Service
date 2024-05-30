package Service;

import Dao.DiagnosesDao;
import Models.Diagnoses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о диагнозах.
 */
public class ServiceDiagnoses {
    private final Logger logger = LoggerFactory.getLogger(ServiceDiagnoses.class);
    private  final DiagnosesDao dao;

    /**
     * Конструктор класса ServiceDiagnoses.
     * @param dao Объект класса DiagnosesDao для доступа к данным о диагнозах.
     */
    public ServiceDiagnoses(DiagnosesDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о диагнозе.
     * @param diagnoses Объект класса Diagnoses с информацией о диагнозе.
     * @param name Наименование диагноза.
     */
    public void updateDiagnoses(Diagnoses diagnoses, String name){
        diagnoses.setName(name);
        dao.update(diagnoses);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о диагнозе.
     * @param diagnoses Объект класса Diagnoses с информацией о диагнозе.
     */
    public void deleteDiagnoses(Diagnoses diagnoses){
        dao.deleteById(diagnoses.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех диагнозах.
     */
    public Collection<Diagnoses> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания нового диагонза.
     * @param name Наименование нового диагноза.
     * @return Объект класса Diagnoses с информацией о новом диагнозе.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public Diagnoses createDiagnoses(String name){
        if(name == null){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        Diagnoses diagnoses = new Diagnoses(0, name);
        dao.save(diagnoses);
        logger.info("Добавление");
        return diagnoses;
    }
}
