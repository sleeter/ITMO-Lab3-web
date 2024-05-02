package entity;

import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Entity
@Table(name = "dots", schema = "s367191")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DotEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "x")
    @Min(-2)
    @Max(2)
    private float x = -2.0f;
    @Column(name = "y")
    @Min(-3)
    @Max(3)
    private float y = 0.0f;
    @Column(name = "r")
    @Min(1)
    @Max(4)
    private float r = 2.0f;
    @Column(name = "status")
    private boolean status;
    @Column(name = "time")
    private String time;
    @Column(name = "scripttime")
    private long scriptTime;

    public String getStringStatus() {
        return status ? "Hit" : "Miss";
    }

    public void updateX(ValueChangeEvent e) {
        String id = ((HtmlSelectBooleanCheckbox) e.getSource()).getId();
        boolean isChecked = (boolean) e.getNewValue();
        if (isChecked) {
            setX(Float.parseFloat(id.substring(5, id.length()).replace("x", ".")));
        }
    }

}
