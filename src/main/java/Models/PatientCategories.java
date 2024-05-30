package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий категории пациентов.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class PatientCategories {
    /** Уникальный идентификатор категории пациента. */
    private int id;

    /** Наименование категории пациента. */
    private String name;

    /** Скидка, применяемая категории пациента. */
    private int discount;

    /**
     * Конструктор класса PatientCategories.
     * @param id Уникальный идентификатор категории пациента.
     * @param name Наименование категории пациента.
     * @param discount Скидка, применяемая категории пациента.
     */
    public PatientCategories(int id, String name, int discount) {
        this.id = id;
        this.name = name;
        this.discount = discount;
    }

    /**
     * Переопределение метода toString() для возвращения наименования категории пациента.
     * @return Наименование категории пациента.
     */
    @Override
    public String toString() {
        return name;
    }
}
