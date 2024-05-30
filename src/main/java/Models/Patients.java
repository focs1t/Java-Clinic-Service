package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий информацию о пациентах.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class Patients {
    /** Уникальный идентификатор пациента. */
    private int id;

    /** Фамилия пациента. */
    private String lastName;

    /** Имя пациента. */
    private String firstName;

    /** Отчество пациента. */
    private String middleName;

    /** Возраст пациента. */
    private int age;

    /** Адрес проживания пациента. */
    private String address;

    /** Уникальный идентификатор категории пациента. */
    private int patientCategoryId;

    /**
     * Конструктор класса Patients.
     * @param id Уникальный идентификатор пациента.
     * @param lastName Фамилия пациента.
     * @param firstName Имя пациента.
     * @param middleName Отчество пациента.
     * @param age Возраст пациента.
     * @param address Адрес проживания пациента.
     * @param patientCategoryId Уникальный идентификатор категории пациента.
     */
    public Patients(int id, String lastName, String firstName, String middleName, int age, String address, int patientCategoryId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.age = age;
        this.address = address;
        this.patientCategoryId = patientCategoryId;
    }

    /**
     * Переопределение метода toString() для возвращения ФИО пациента в виде строки.
     * @return Фамилия, имя и отчество пациента в виде строки.
     */
    @Override
    public String toString() {
        return lastName + ' ' + firstName + ' ' + middleName;
    }
}
