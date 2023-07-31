package rc1.bored.commands;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import org.springframework.shell.standard.ShellOption;
import rc1.bored.client.BoredApiClient;
import rc1.bored.model.BoredActivity;

import org.springframework.web.client.RestTemplate;


import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Command
@Slf4j
@ShellComponent
public class BoredCommands extends BoredApiClient {
    @ShellMethod("Gets random Activity from boredapi.com/api/activity")
    public BoredActivity get(
                    @ShellOption(value = "t", defaultValue = "") String type,
                    @ShellOption(value = "a", defaultValue = "") String accessibility,
                    @ShellOption(value = "p", defaultValue = "") String participants,
                    @ShellOption(value = "c", defaultValue = "") String cost,
                    @ShellOption(value = "k", defaultValue = "") String key
    ) {
        Map<String, String> shellArgs = new HashMap<>();
        shellArgs.put("type=", type);
        shellArgs.put("accessibility=", accessibility);
        shellArgs.put("participants=", participants);
        shellArgs.put("price=", cost);
        shellArgs.put("key=", key);

        String args = "";

        // args is a string which will be concatenated to the end of url request.
        // the following for each loop checks if Argument Map is empty or not.
        //if user has made input, then this input will be added to args string
        for (Map.Entry<String, String> arg : shellArgs.entrySet()) {
            if (arg != null && !arg.getValue().equals("")) {
                if (!args.isEmpty()) {
                    args = args.concat("&");
                    log.info("Adding &");
                }
                args = args.concat(arg.getKey() + arg.getValue());
            }
        }
        String requestUrl = "https://boredapi.com/api/activity?" + args;
        return getDataFromApi(requestUrl);
    }
}
