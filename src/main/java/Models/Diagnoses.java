package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий диагнозы.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class Diagnoses {
    /** Уникальный идентификатор диагноза. */
    private int id;

    /** Наименование диагноза. */
    private String name;

    /**
     * Конструктор класса Diagnoses.
     * @param id Уникальный идентификатор диагноза.
     * @param name Наименование диагноза.
     */
    public Diagnoses(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Переопределение метода toString() для возвращения наименования диагноза.
     * @return Наименование диагноза.
     */
    @Override
    public String toString() {
        return name;
    }
}
