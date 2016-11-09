/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.domain.beans;

/**
 *
 * @author vanilka
 */
public class Relation {
    
    private Long id;
    private Member SimLeft;
    private Member SimRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getSimLeft() {
        return SimLeft;
    }

    public void setSimLeft(Member SimLeft) {
        this.SimLeft = SimLeft;
    }

    public Member getSimRight() {
        return SimRight;
    }

    public void setSimRight(Member SimRight) {
        this.SimRight = SimRight;
    }
    
    
    
}
