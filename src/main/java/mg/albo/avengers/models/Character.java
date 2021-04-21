package mg.albo.avengers.models;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Character {
    private String name;
    private Set<String> comics = new HashSet<>();

    public void addComic(String comic) {
        this.comics.add(comic);
    }
}
