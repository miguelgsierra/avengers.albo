package mg.albo.avengers.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody {
    private int code;
    private String status;
    private Data data;
}
