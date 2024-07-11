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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ConferenceRoomServlet", urlPatterns = {"/conferenceRooms"})
public class ConferenceRoomServlet extends HttpServlet {

    private ResourceServiceImpl resourceServiceImpl;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        resourceServiceImpl = new ResourceServiceImpl(new ResourceRepositoryImpl());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ConferenceRoom> conferenceRooms = new ArrayList<>(resourceServiceImpl.getAllConferenceRooms().values());
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), conferenceRooms);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConferenceRoomDTO conferenceRoomDTO = objectMapper.readValue(req.getInputStream(), ConferenceRoomDTO.class);
        ConferenceRoom conferenceRoom = new ConferenceRoom(null, conferenceRoomDTO.getName(), false);
        resourceServiceImpl.addConferenceRoom(conferenceRoom);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getOutputStream(), "Conference room added successfully.");
    }
}
