package com.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Long value;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "comment")
    private String comment;

    public Rating(){}

    public Rating(Long value, Long groupId, String comment){
        this.value = value;
        this.groupId = groupId;
        this.comment = comment;
    }

    public Long getValue(){
        return this.value;
    }

    public Long getGroupId(){
        return this.groupId;
    }

    public String getComment(){
        return this.comment;
    }
}
