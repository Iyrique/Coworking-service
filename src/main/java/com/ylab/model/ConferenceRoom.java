package com.ylab.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a conference room in the coworking service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "conference_rooms", schema = "coworking")
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "conference_room_sequence")
    @SequenceGenerator(name = "conference_room_sequence", sequenceName = "conference_room_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_available")
    private boolean isAvailable;

}
