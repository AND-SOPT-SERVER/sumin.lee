package org.sopt.week3;
import jakarta.persistence.*;
import jakarta.persistence.Id;
/**
 * create table sopt_member(
 *      id bigint primary key,
 *      name varchar(16) not null,
 *      age int not null
 *      );
 *      *
 * **/
@Entity
@Table(name = "sopt-member")
public class SoptMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name="age",nullable = false)
    private int age;

    public SoptMemberEntity(){}
    public SoptMemberEntity(String name,int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
