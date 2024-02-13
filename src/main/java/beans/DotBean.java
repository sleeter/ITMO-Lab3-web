package beans;

import checker.AreaChecker;
import db.DotDao;
import entity.DotEntity;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Named(value = "dotBean")
@SessionScoped
@ManagedBean
@Data
public class DotBean  implements Serializable {
    private DotEntity dot;
    @Inject
    private DotDao dotDao;
    private List<DotEntity> dotsList = new ArrayList<>();
    private int timezone;

    @PostConstruct
    public void postConstruct() {
        dot = new DotEntity();
        dotDao.createEntityManager();
        dotsList = dotDao.getDotsFromDB();
    }

    public void add() {
        long timer = System.nanoTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        String currentTime = formatter.format(LocalDateTime.now().minus(getTimezone(), MINUTES));
        dot.setStatus(AreaChecker.check(dot));
        dot.setTime(currentTime);
        dot.setScriptTime((long) ((System.nanoTime() - timer) * 0.001) );
        dotsList.add(dot);
        dotDao.addDotToDB(dot);

        DotEntity oldDot = dot;
        dot = new DotEntity();
        dot.setX(oldDot.getX());
        dot.setY(oldDot.getY());
        dot.setR(oldDot.getR());
    }

    public void clear() {
        dotDao.clearDotsInDB();
        dotsList = dotDao.getDotsFromDB();
    }

}
