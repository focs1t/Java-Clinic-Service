package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий специальности врачей.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class DoctorSpecialties {
    /** Уникальный идентификатор специальности врача. */
    private int id;

    /** Наименование специальности врача. */
    private String name;

    /**
     * Конструктор класса DoctorSpecialties.
     * @param id Уникальный идентификатор специальности врача.
     * @param name Наименование специальности врача.
     */
    public DoctorSpecialties(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Переопределение метода toString() для возвращения наименования специальности врача.
     * @return Наименование специальности врача.
     */
    @Override
    public String toString() {
        return name;
    }
}
