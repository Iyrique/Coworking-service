package com.ylab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a workspace in the coworking service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspaces", schema = "coworking")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspace_sequence")
    @SequenceGenerator(name = "workspace_sequence", sequenceName = "workspace_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_available")
    private boolean isAvailable;


}
