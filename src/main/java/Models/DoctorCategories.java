package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий категории врачей.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class DoctorCategories {
    /** Уникальный идентификатор категории врача. */
    private int id;

    /** Наименование категории врача. */
    private String name;

    /**
     * Конструктор класса DoctorCategories.
     * @param id Уникальный идентификатор категории врача.
     * @param name Наименование категории врача.
     */
    public DoctorCategories(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Переопределение метода toString() для возвращения наименования категории врача.
     * @return Наименование категории врача.
     */
    @Override
    public String toString() {
        return name;
    }
}