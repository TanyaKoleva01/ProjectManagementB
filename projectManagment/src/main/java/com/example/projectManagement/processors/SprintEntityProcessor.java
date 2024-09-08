package com.example.projectManagement.processors;

import com.example.projectManagement.controllers.SprintController;
import com.example.projectManagement.models.Sprint;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@AllArgsConstructor
public class SprintEntityProcessor implements RepresentationModelProcessor<EntityModel<Sprint>> {

    @Override
    public EntityModel<Sprint> process(EntityModel<Sprint> model) {
        var sprint = model.getContent();
        assert sprint != null;
        model.add(linkTo(methodOn(SprintController.class).addTasks(sprint.getId(), new ArrayList<>())).withRel("addTasks"));
        return model;
    }
}