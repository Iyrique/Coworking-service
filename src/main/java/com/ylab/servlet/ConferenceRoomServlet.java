package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.ConferenceRoomDTO;
import com.ylab.model.ConferenceRoom;
import com.ylab.repository.ResourceRepository;
import com.ylab.service.ResourceService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ConferenceRoomServlet", urlPatterns = {"/conferenceRooms"})
public class ConferenceRoomServlet extends HttpServlet {

    private ResourceService resourceService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        resourceService = new ResourceService(new ResourceRepository());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ConferenceRoom> conferenceRooms = new ArrayList<>(resourceService.getAllConferenceRooms().values());
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), conferenceRooms);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConferenceRoomDTO conferenceRoomDTO = objectMapper.readValue(req.getInputStream(), ConferenceRoomDTO.class);
        ConferenceRoom conferenceRoom = new ConferenceRoom(null, conferenceRoomDTO.getName(), false);
        resourceService.addConferenceRoom(conferenceRoom);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getOutputStream(), "Conference room added successfully.");
    }
}
