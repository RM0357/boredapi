package rc1.bored;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.RestTemplate;
import rc1.bored.commands.BoredCommands;
import rc1.bored.model.BoredActivity;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotEquals;

public class BoredApplicationTests {

    @Autowired
    BoredCommands boredCommand;

    @Autowired
    ShellOption shell;

    @MockBean
    RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        boredCommand = new BoredCommands(); // Initialize the boredCommand object
    }



    @InjectMocks
    private BoredCommands myBoredActivityCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Unit test
    @Test
    public void testGetMultipleValuesFromShell() {
        // Given
        String type = "social";
        String participants = "2";
        // Simulate the shell input
        Input input = new Input() {
            @Override
            public String rawText() {
                return String.format("get --t %s  --p %s", type, participants);
            }
        };
        BoredActivity result = myBoredActivityCommand.get(type,"", participants,"","");
        assertEquals("ExpectedResult","social", result.getType());
        assertEquals("ExpectedResult",2, result.getParticipants());
    }

    //Integration tests:
    @Test
    public void testGetMethodWith2Filters() {
        BoredActivity result = boredCommand.get("social","","2","","");
        assertEquals("Expected:","social",result.getType());
        assertEquals("Expected:",2,(result.getParticipants()));
    }
    @Test
    public void testGetSpecificActivityWithKey() {
        BoredActivity result = boredCommand.get("","","","","2277801");
        assertEquals("Expected:",2277801,result.getKey());
        assertEquals("Expected:","Write a handwritten letter to somebody",result.getActivity());
    }
    @Test
    public void testGetEmptyResultWhenKeyDoesntExist() {
        BoredActivity result = boredCommand.get("","","","","80000");
        assertEquals("Expected:",0,result.getKey());
    }

    @BeforeEach
    public void setupT() {
        RestTemplate restTemplate = new RestTemplate(); // Initialize the boredCommand object
    }
    @Test
    public void testGet200okResponse() {
        String url = "https://boredapi.com/api/activity?";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BoredActivity> response = restTemplate.getForEntity(url, BoredActivity.class);
        BoredActivity data = response.getBody();
        System.out.println(response.getStatusCode());
        assertEquals("Expected:",200,response.getStatusCode().value());
    }
}