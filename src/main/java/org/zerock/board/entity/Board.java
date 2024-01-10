package org.zerock.board.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.stream.Stream;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")//writer을 제외한다.
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; //연관 관계 지정-> Member.email = Board.email

    public void changeTitle(String title){
        this.title =title;
    }

    public void changContent(String content){
        this.content =content;
    }

}
