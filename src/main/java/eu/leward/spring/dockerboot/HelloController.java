package eu.leward.spring.dockerboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final AppConfiguration appConfiguration;

    @Autowired
    public HelloController(AppConfiguration appConfiguration) {
        this.appConfiguration = appConfiguration;
    }

    @GetMapping("")
    public Map<String, String> index() {
        Map<String, String> result = new HashMap<>();
        result.put("hello", appConfiguration.getHello());
        result.put("world", appConfiguration.getWorld());
        result.put("greeting", String.format("%s %s!", appConfiguration.getHello(), appConfiguration.getWorld()));
        result.put("cwd", getCWD());
        return result;
    }

    public static String getCWD() {
        File file;
        int index;
        String pathSeparator;
        String cwd = null;

        file = new File(".");
        pathSeparator = File.separator;
        index = file.getAbsolutePath().lastIndexOf(pathSeparator);

        try {
            cwd = file.getAbsolutePath().substring(0, index);
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Caught Exception: " + e.getMessage() + "\n");
        }

        return cwd;
    }

}
