package mg.albo.avengers.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import lombok.Getter;

public class Constants {
    public static final String EDITOR = "editor";
    public static final String WRITER = "writer";
    public static final String COLORIST = "colorist";

    @Getter
    public static class AvengerType {
        private int id;
        private String name;

        public AvengerType(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public final static Map<String, AvengerType> avengersAvailables = new HashMap<>() {
        {
            put("ironman", new AvengerType(1009368, "Iron Man"));
            put("capamerica", new AvengerType(1009220, "Captain America"));
        }
    };

    public static String getTimeFormated(long time) {
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getDefault());
        return formatter.format(date);
    }
}
