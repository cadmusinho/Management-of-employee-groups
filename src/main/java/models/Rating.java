package main.java.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private int value;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "comment")
    private String comment;

    public Rating(){}

    public Rating(int value, Long groupId, String comment){
        if(value >= 6) {
            this.value = 0;
        }
        else {
            this.value = value;
        }
        this.groupId = groupId;
        this.comment = comment;
    }
}
