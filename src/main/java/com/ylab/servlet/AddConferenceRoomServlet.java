package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.model.ConferenceRoom;
import com.ylab.repository.impl.ResourceRepositoryImpl;
import com.ylab.service.impl.ResourceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.io.IOException;
import java.util.Set;

@WebServlet("/addConferenceRoom")
public class AddConferenceRoomServlet extends HttpServlet {

    private final ResourceServiceImpl resourceServiceImpl;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    public AddConferenceRoomServlet() {
        this.resourceServiceImpl = new ResourceServiceImpl(new ResourceRepositoryImpl());
        this.objectMapper = new ObjectMapper();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConferenceRoomDTO conferenceRoomDTO = objectMapper.readValue(req.getInputStream(), ConferenceRoomDTO.class);
        Set<ConstraintViolation<ConferenceRoomDTO>> violations = validator.validate(conferenceRoomDTO);

        if (!violations.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), violations);
            return;
        }

        ConferenceRoom conferenceRoom = new ConferenceRoom(null, conferenceRoomDTO.getName(), conferenceRoomDTO.isAvailable());
        resourceServiceImpl.addConferenceRoom(conferenceRoom);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getWriter(), "Conference room added successfully");
    }
}

