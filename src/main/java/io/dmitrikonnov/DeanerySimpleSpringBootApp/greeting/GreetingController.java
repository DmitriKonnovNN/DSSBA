package io.dmitrikonnov.DeanerySimpleSpringBootApp.greeting;

//import io.dmitrikonnov.DeanerySimpleSpringBootApp.PrePostProcTestFeatures.Profiling;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
//@Profiling
public class GreetingController {

    /*private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public GreetingController(AuthenticationManager authenticationManager,
                              UserService userService,
                              JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @PostMapping("authenticate")
    public ResponseEntity <?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }*/
    @GetMapping ("/")
    public String getGreeting (Map<String, Object> model) {
        System.out.println("CHECK CHECK");
        return "index";
    }


    @GetMapping("login")
    public String getLoginView (){
        return "login";
    }

   /* @GetMapping("greeting")
    public String getGreeting (@PathParam(value = "firstName") String firstName,
                               @PathParam (value = "secondName") String secondName) {
        return "Hi " + firstName +" "+secondName + "!";
    }*/
    /*the same mapping beneath, but there string variables must have equal names as URI-params*/

//    @ApiOperation(value = "get  greeted to passed first and second name",
//    notes = "provide at least first name or first and second name to be greeted",
//    response = String.class)
//   @GetMapping("greeting")
//    public String getGreeting (@ApiParam(value = "first name to be greeted", required = true) @RequestParam String firstName,
//                               @ApiParam(value = "second name to be greeted", defaultValue = "mudakov")
//                               @RequestParam (required = false, defaultValue = "Mudakov") String secondName) {
//        return "Hi " + firstName +" "+secondName + "!";
//    }

    @GetMapping("greeting")
    public String getGreetingView () {
        System.out.println("CHECK CHECK CHECK VIEW GREETING CONTROLLER IS CALLED");
        return "greeting";
    }


    @GetMapping ("greetingtime")

    public ResponseEntity <String> getGreetingByTime (@RequestParam (defaultValue = "User") String name,
                                                @RequestParam (defaultValue = "day") String time){
        List<String> times = Arrays.asList("evening", "morning", "day", "night");

        if (times.contains(time)) return ResponseEntity.ok("Good " + time + ", " + name + "!");
        else return ResponseEntity.badRequest().body("Time should be: morning, day, evening, night !");
    }



}

