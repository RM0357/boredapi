package rc1.bored.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter @Setter
@Data
public class BoredActivity {
    String  activity;
    String type;
    int participants;
    double price;
    String link;
    int key;
    double accessibility;
    public BoredActivity() {
    }
}
