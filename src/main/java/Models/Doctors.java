package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий врачей.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class Doctors {
    /** Уникальный идентификатор врача. */
    private int id;

    /** Фамилия врача. */
    private String lastName;

    /** Имя врача. */
    private String firstName;

    /** Отчество врача. */
    private String middleName;

    /** Уникальный идентификатор специальности врача. */
    private int doctorSpecialtyId;

    /** Уникальный идентификатор категории врача. */
    private int doctorCategoryId;

    /**
     * Конструктор класса Doctors.
     * @param id Уникальный идентификатор врача.
     * @param lastName Фамилия врача.
     * @param firstName Имя врача.
     * @param middleName Отчество врача.
     * @param doctorSpecialtyId Уникальный идентификатор специальности врача.
     * @param doctorCategoryId Уникальный идентификатор категории врача.
     */
    public Doctors(int id, String lastName, String firstName, String middleName, int doctorSpecialtyId, int doctorCategoryId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.doctorSpecialtyId = doctorSpecialtyId;
        this.doctorCategoryId = doctorCategoryId;
    }

    /**
     * Переопределение метода toString() для возвращения полного имени врача.
     * @return Полное имя врача.
     */
    @Override
    public String toString() {
        return lastName + ' ' + firstName + ' ' + middleName;
    }
}
