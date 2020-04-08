package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.interfaces.IEnum;
import beau.taapken.dogadoption.logic.EnumLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/enum")
@RestController
public class EnumController implements IEnum {
    @Autowired
    EnumLogic enumLogic;

    @GetMapping("/getdogbreeds")
    public String getDogBreeds() {
        return enumLogic.getDogBreeds();
    }
}
