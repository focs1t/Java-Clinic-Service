package Service;


import Dao.PatientsDao;
import Models.Patients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о пациентах.
 */
public class ServicePatients {
    private final Logger logger = LoggerFactory.getLogger(ServicePatients.class);
    private  final PatientsDao dao;

    /**
     * Конструктор класса ServicePatients.
     * @param dao Объект класса PatientsDao для доступа к данным о пациентах.
     */
    public ServicePatients(PatientsDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о пациенте.
     * @param patients Объект класса Patients с информацией о пациенте.
     * @param lastName Фамилия пациента.
     * @param firstName Имя пациента.
     * @param middleName Отчество пациента.
     * @param age Возраст пациента.
     * @param address Адрес пациента.
     * @param patientCategoryId Уникальный идентификатор категории пациента.
     */
    public void updatePatients(Patients patients, String lastName, String firstName, String middleName, int age, String address, int patientCategoryId){
        patients.setLastName(lastName);
        patients.setFirstName(firstName);
        patients.setMiddleName(middleName);
        patients.setAge(age);
        patients.setAddress(address);
        patients.setPatientCategoryId(patientCategoryId);
        dao.update(patients);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о пациенте.
     * @param patients Объект класса Patients с информацией о пациенте.
     */
    public void deletePatients(Patients patients){
        dao.deleteById(patients.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех пациентах.
     */
    public Collection<Patients> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания нового пациента.
     * @param lastName Фамилия пациента.
     * @param firstName Имя пациента.
     * @param middleName Отчество пациента.
     * @param age Возраст пациента.
     * @param address Адрес пациента.
     * @param patientCategoryId Уникальный идентификатор категории пациента.
     * @return Объект класса Patients с информацией о новом пациенте.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public Patients createPatients(String lastName, String firstName, String middleName, int age, String address, int patientCategoryId){
        if(lastName == null || firstName == null || middleName == null || age <= 0 || address == null || patientCategoryId <= 0){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        Patients patients = new Patients(0, lastName, firstName, middleName, age, address, patientCategoryId);
        dao.save(patients);
        logger.info("Добавление");
        return patients;
    }
}
