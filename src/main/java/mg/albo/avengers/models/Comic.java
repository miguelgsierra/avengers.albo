package mg.albo.avengers.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Comic {
    private int id;
    private String title;
    private DataCreators creators;
    private DataCharacters characters;
}
