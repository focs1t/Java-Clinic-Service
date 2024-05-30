package Models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

/**
 * Класс, представляющий обращения пациентов.
 */
@Data
@NoArgsConstructor
@Getter
@Setter
public class Appeals {
    /** Уникальный идентификатор обращения. */
    private int id;

    /** Дата обращения. */
    private Date data;

    /** Уникальный идентификатор пациента, к которому относится обращение. */
    private int patientId;

    /**
     * Конструктор класса Appeals.
     * @param id Уникальный идентификатор обращения.
     * @param data Дата обращения.
     * @param patientId Уникальный идентификатор пациента.
     */
    public Appeals(int id, Date data, int patientId) {
        this.id = id;
        this.data = data;
        this.patientId = patientId;
    }

    /**
     * Переопределение метода toString() для возвращения идентификатора обращения в виде строки.
     * @return Уникальный идентификатор обращения в виде строки.
     */
    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
