package org.launchcode.techjobs.persistent.controllers;


import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "skills")
public class SkillController {
    @Autowired
    private SkillRepository  skillRepository;

    @RequestMapping(value="/")
    public String index(Model model) {
        model.addAttribute("skills", skillRepository.findAll());
        return "skills/index";
    }

    @GetMapping("add")
    public String displayAddSkillForm(Model model) {
        model.addAttribute(new Skill());
        return "skills/add";
    }

    @PostMapping("add")
    public String processAddSkillForm(@ModelAttribute @Valid Skill newSkills,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skills/add";
        }
        skillRepository.save(newSkills);

        return "redirect:";
    }

    @GetMapping("view/{skillID}")
    public String displayViewSkill(Model model, @PathVariable int skillID) {

        Optional optSkill = skillRepository.findById(skillID);
        if (optSkill.isPresent()) {
            Skill skill = (Skill) optSkill.get();
            model.addAttribute("skill", skill);
            return "skills/view";
        } else {
            return "redirect:../";
        }

    }
}
