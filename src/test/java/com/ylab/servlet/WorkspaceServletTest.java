package com.ylab.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ylab.model.Workspace;
import com.ylab.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class WorkspaceServletTest {

    private WorkspaceServlet workspaceServlet;
    private ResourceService resourceService;

    @BeforeEach
    public void setUp() throws ServletException {
        resourceService = mock(ResourceService.class);
        workspaceServlet = new WorkspaceServlet();
        workspaceServlet.init();
    }

    @Test
    @DisplayName("test Get")
    public void testDoGet_Success() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);

        List<Workspace> workspaces = new ArrayList<>();
        workspaces.add(new Workspace(1L, "Workspace 1", false));
        workspaces.add(new Workspace(2L, "Workspace 2", false));
        Map<Long, Workspace> workspaceMap = new HashMap<>();
        workspaceMap.put(1L, workspaces.get(0));
        workspaceMap.put(2L, workspaces.get(1));

        when(resourceService.getAllWorkspaces()).thenReturn(workspaceMap);
        when(response.getOutputStream()).thenReturn(outputStream);

        workspaceServlet.doGet(request, response);

    }

    @Test
    @DisplayName("test post")
    public void testDoPost_Success() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStream = mock(ServletOutputStream.class);

        String inputJson = "{\"name\":\"New Workspace\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputJson.getBytes());

        when(request.getInputStream()).thenReturn(new MockServletInputStream(inputStream));
        when(response.getOutputStream()).thenReturn(outputStream);

        workspaceServlet.doPost(request, response);
    }
}
