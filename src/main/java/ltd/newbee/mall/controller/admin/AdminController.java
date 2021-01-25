package ltd.newbee.mall.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/indexAll")
    public String indexAll() {
        return "admin/index-all";
    }

    @GetMapping("/index")
    public String index() {
        return "admin/index";
    }

    @GetMapping("/category")
    public String category() {
        return "admin/category";
    }
}
