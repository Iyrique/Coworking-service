package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.dto.WorkspaceDTO;
import com.ylab.model.Workspace;
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

@WebServlet(name = "WorkspaceServlet", urlPatterns = {"/workspaces"})
public class WorkspaceServlet extends HttpServlet {

    private ResourceService resourceService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        resourceService = new ResourceService(new ResourceRepository());
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Workspace> workspaces = new ArrayList<>(resourceService.getAllWorkspaces().values());
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(resp.getOutputStream(), workspaces);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WorkspaceDTO workspaceDTO = objectMapper.readValue(req.getInputStream(), WorkspaceDTO.class);
        Workspace workspace = new Workspace(null, workspaceDTO.getName(), false);
        resourceService.addWorkspace(workspace);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        objectMapper.writeValue(resp.getOutputStream(), "Workspace added successfully.");
    }
}
