package beans;

import db.DotDao;
import entity.DotEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import util.MBeanRegistryUtil;

import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

@ApplicationScoped
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntervalTimer extends NotificationBroadcasterSupport implements IntervalTimerMBean, Serializable {
    @Inject
    private DotEntity dot;
    @Inject
    private DotDao dao;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.registerBean(this, "timeBetweenRequests");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }
    @Override
    public double getAverageTimeBetweenRequests() {
        List<DotEntity> points = dao.getDotsFromDB();
        long averageTime = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
        for(int i = 0; i < points.size() - 1; i++) {
            try {
                averageTime += format.parse(points.get(i + 1).getTime()).getTime() - format.parse(points.get(i).getTime()).getTime();
            }catch (Exception e) {
                continue;
            }
        }
        return (double) averageTime / (points.size() - 1);
    }
}

