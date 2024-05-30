package Service;

import Dao.DoctorsDao;
import Models.Doctors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о  врачах.
 */
public class ServiceDoctors {
    private final Logger logger = LoggerFactory.getLogger(ServiceDoctors.class);
    private  final DoctorsDao dao;

    /**
     * Конструктор класса ServiceDoctors.
     * @param dao Объект класса DoctorsDao для доступа к данным о врачах.
     */
    public ServiceDoctors(DoctorsDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о враче.
     * @param doctors Объект класса Doctors с информацией о враче.
     * @param lastName Фамилия врача.
     * @param firstName Имя врача.
     * @param middleName Отчество врача.
     * @param doctorSpecialtyId Уникальный идентификатор специальности врача.
     * @param doctorCategoryId Уникальный идентификатор категории врача.
     */
    public void updateDoctors(Doctors doctors, String lastName, String firstName, String middleName, int doctorSpecialtyId, int doctorCategoryId){
        doctors.setLastName(lastName);
        doctors.setFirstName(firstName);
        doctors.setMiddleName(middleName);
        doctors.setDoctorSpecialtyId(doctorSpecialtyId);
        doctors.setDoctorCategoryId(doctorCategoryId);
        dao.update(doctors);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о враче.
     * @param doctors Объект класса Doctors с информацией о враче.
     */
    public void deleteDoctors(Doctors doctors){
        dao.deleteById(doctors.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех врачах.
     */
    public Collection<Doctors> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания нового врача.
     * @param lastName Фамилия врача.
     * @param firstName Имя врача.
     * @param middleName Отчество врача.
     * @param doctorSpecialtyId Уникальный идентификатор специальности врача.
     * @param doctorCategoryId Уникальный идентификатор категории врача.
     * @return Объект класса Doctors с информацией о новом враче.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public Doctors createDoctors(String lastName, String firstName, String middleName, int doctorSpecialtyId, int doctorCategoryId){
        if(lastName == null || firstName == null || middleName == null || doctorSpecialtyId <= 0 || doctorCategoryId <= 0){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        Doctors doctors = new Doctors(0, lastName, firstName, middleName, doctorSpecialtyId, doctorCategoryId);
        dao.save(doctors);
        logger.info("Добавление");
        return doctors;
    }
}
