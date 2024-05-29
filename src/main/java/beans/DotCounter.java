package beans;

import db.DotDao;
import entity.DotEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.Destroyed;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.MBeanRegistryUtil;
import checker.AreaChecker;

import javax.management.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Named
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class DotCounter extends NotificationBroadcasterSupport implements DotCounterMBean, Serializable {
    @Inject
    private DotDao dao;
    @Inject
    private DotEntity dot;
    private final List<DotEntity> results = new ArrayList<>();
    int sequenceNumber = 0;
    private int timezone;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        // Deprecated because of the profiler
//        results.addAll(storage.getAllResults());
        MBeanRegistryUtil.registerBean(this, "countOfDots");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }
    @Override
    public int getCountOfDots() {
        return results.size();
    }
    @Override
    public int getCountOfAreaDots() {
        return (int) results.stream().filter(DotEntity::isStatus).count();
    }
    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "The point is outside of area.";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }
    public void addResult() {
        // Deprecated because of the profiler
//        storage.addResult(result);
        long timer = System.nanoTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        String currentTime = formatter.format(LocalDateTime.now().minusMinutes(getTimezone()));
        dot.setStatus(AreaChecker.check(dot));
        dot.setTime(currentTime);
        dot.setScriptTime((long) ((System.nanoTime() - timer) * 0.001) );
        dao.addDotToDB(dot);
        results.add(dot);
        if (!dot.isStatus()) {
            Notification notification = new Notification(
                    "Three misses in a row",
                    getClass().getSimpleName(),
                    sequenceNumber++,
                    "The user missed three times in a row."
            );
            sendNotification(notification);
        }
    }
    public void clearResult() {
        dao.clearDotsInDB();
        results.clear();
    }

}
