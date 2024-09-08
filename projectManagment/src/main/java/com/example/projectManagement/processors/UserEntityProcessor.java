package com.example.projectManagement.processors;

import com.example.projectManagement.controllers.UserController;
import com.example.projectManagement.models.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserEntityProcessor implements RepresentationModelProcessor<EntityModel<User>> {

    @Override
    public EntityModel<User> process(EntityModel<User> model) {
        User user = model.getContent();
        assert user != null;
        model.add(linkTo(methodOn(UserController.class).assignTasks(user.getId(), new ArrayList<Long>()))
                .withRel("assignTasks"));
        return model;
    }
}