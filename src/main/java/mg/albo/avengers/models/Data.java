package mg.albo.avengers.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Data {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private Comic[] results;
}
