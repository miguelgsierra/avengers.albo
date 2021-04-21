package mg.albo.avengers.documents;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mg.albo.avengers.models.Character;
import mg.albo.avengers.models.Creator;
import mg.albo.avengers.utils.Constants;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "avengers")
public class Avenger {

    @Id
    private String id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Marvel id cannot be null")
    private int marvelID;

    @NotNull(message = "Last sync time cannot be null")
    private long lastSync;

    private Set<String> editors = new HashSet<>();
    private Set<String> writers = new HashSet<>();
    private Set<String> colorists = new HashSet<>();
    private Set<Character> characters = new HashSet<>();

    public Avenger(int marvelID, String name) {
        this.marvelID = marvelID;
        this.name = name;
    }

    public void addCreator(Creator creator) {
        if (creator.getRole().equals(Constants.EDITOR))
            this.editors.add(creator.getName());
        else if (creator.getRole().equals(Constants.WRITER))
            this.writers.add(creator.getName());
        else if (creator.getRole().equals(Constants.COLORIST))
            this.colorists.add(creator.getName());
    }

    public void addCharacter(String character, String comic) {
        Character found = this.characters.stream().filter(c -> character.equals(c.getName())).findFirst().orElse(null);
        if (found == null) {
            found = new Character();
            found.setName(character);
        }

        found.addComic(comic);
        this.characters.add(found);
    }

    public Map<String, Object> getColaboratorsResult() {
        Map<String, Object> map = new HashMap<>();

        String last_sync = "Fecha de última sincronización en " + Constants.getTimeFormated(this.getLastSync());
        map.put("last_sync", last_sync);
        map.put("editors", this.getEditors());
        map.put("writers", this.getWriters());
        map.put("colorists", this.getColorists());
        return map;
    }
}
