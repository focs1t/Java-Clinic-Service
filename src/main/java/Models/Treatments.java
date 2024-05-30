package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс, представляющий информацию о лечении пациентов.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class Treatments {
    /** Уникальный идентификатор лечения. */
    private int id;

    /** Уникальный идентификатор обращения пациента. */
    private int appealId;

    /** Уникальный идентификатор врача, проводящего лечение. */
    private int doctorId;

    /** Уникальный идентификатор диагноза, определенного пациенту. */
    private int diagnosisId;

    /** Цена лечения. */
    private int price;

    /**
     * Конструктор класса Treatments.
     * @param id Уникальный идентификатор лечения.
     * @param appealId Уникальный идентификатор обращения пациента.
     * @param doctorId Уникальный идентификатор врача.
     * @param diagnosisId Уникальный идентификатор диагноза.
     * @param price Цена лечения.
     */
    public Treatments(int id, int appealId, int doctorId, int diagnosisId, int price) {
        this.id = id;
        this.appealId = appealId;
        this.doctorId = doctorId;
        this.diagnosisId = diagnosisId;
        this.price = price;
    }
}
