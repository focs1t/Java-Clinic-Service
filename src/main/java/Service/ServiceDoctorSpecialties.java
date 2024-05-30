package Service;


import Dao.DoctorSpecialtiesDao;
import Models.DoctorSpecialties;
import Models.Doctors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Класс, предоставляющий сервисы для работы с данными о специальностях врачей.
 */
public class ServiceDoctorSpecialties {
    private final Logger logger = LoggerFactory.getLogger(ServiceDoctorSpecialties.class);
    private  final DoctorSpecialtiesDao dao;

    /**
     * Конструктор класса ServiceDoctorSpecialties.
     * @param dao Объект класса DoctorSpecialtiesDao для доступа к данным о специальностях врачей.
     */
    public ServiceDoctorSpecialties(DoctorSpecialtiesDao dao) {
        this.dao = dao;
    }

    /**
     * Метод для обновления информации о специальности врача.
     * @param doctorSpecialties Объект класса DoctorSpecialties с информацией о специальности врача.
     * @param name Наименование специальности врача.
     */
    public void updateDoctorSpecialties(DoctorSpecialties doctorSpecialties, String name){
        doctorSpecialties.setName(name);
        dao.update(doctorSpecialties);
        logger.info("Обновление");
    }

    /**
     * Метод для удаления информации о специальности врача.
     * @param doctorSpecialties Объект класса DoctorSpecialties с информацией о специальности врача.
     */
    public void deleteDoctorSpecialties(DoctorSpecialties doctorSpecialties){
        dao.deleteById(doctorSpecialties.getId());
        logger.info("Удаление");
    }

    /**
     * Метод для просмотра информации о всех специальностях врачей.
     */
    public Collection<DoctorSpecialties> ViewField(){
        return dao.finAll();
    }

    /**
     * Метод для создания новой специальности врача.
     * @param name Наименование новой специальности врача.
     * @return Объект класса DoctorSpecialties с информацией о новой специальности врача.
     * @throws IllegalArgumentException если переданы некорректные параметры.
     */
    public DoctorSpecialties createDoctorSpecialties(String name){
        if(name == null){
            throw  new IllegalArgumentException("Wrong parameters");
        }
        DoctorSpecialties doctorSpecialties = new DoctorSpecialties(0, name);
        dao.save(doctorSpecialties);
        logger.info("Добавление");
        return doctorSpecialties;
    }

    /**
     * Метод для поиска врачей по идентификатору специальности.
     * @param doctorId Уникальный идентификатор врача.
     * @return Объект класса Doctors соответствующего врача.
     * @throws SQLException если возникает ошибка при работе с базой данных.
     */
    public Collection<Doctors> FindByDoctorId(int doctorId) throws SQLException {
        logger.info("Поиск");
        return dao.findByDoctorId(doctorId);
    }
}
