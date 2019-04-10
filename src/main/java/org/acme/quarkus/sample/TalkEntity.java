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
public class TalkEntity extends PanacheEntity {
    public String title;
    @Lob
    public String description;
    
    @JsonbTransient
    @ManyToMany(cascade = CascadeType.ALL)
    public List<SpeakerEntity> speakers = new ArrayList<>();
}
