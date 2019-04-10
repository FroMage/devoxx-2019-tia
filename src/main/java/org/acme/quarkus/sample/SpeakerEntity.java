package org.acme.quarkus.sample;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class SpeakerEntity extends PanacheEntity {

    public String name;
    @Lob
    public String biography;
    public String company;
    public String avatar;
    @JsonbTransient
    @ManyToMany(mappedBy = "speakers", cascade = CascadeType.ALL)
    public List<TalkEntity> talks = new ArrayList<>();
}
