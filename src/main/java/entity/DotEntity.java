package entity;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.html.HtmlSelectBooleanCheckbox;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "dots")
@SessionScoped
@NoArgsConstructor
@Data
public class DotEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column(name = "x")
    private float x = 0.0f;
    @Column(name = "y")
    private float y = 0.0f;
    @Column(name = "r")
    private float r = 2.0f;
    @Column(name = "status")
    private boolean status;
    @Column(name = "time")
    private String time;
    @Column(name = "script_time")
    private long scriptTime;

    public String getStringStatus() {
        return status ? "Hit" : "Miss";
    }
}
