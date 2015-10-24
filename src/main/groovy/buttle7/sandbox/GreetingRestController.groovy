package buttle7.sandbox

import org.springframework.web.bind.annotation.*

@RestController
class GreetingRestController {
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(name);
    }
}
