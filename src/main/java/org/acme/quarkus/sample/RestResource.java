package org.acme.quarkus.sample;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.quarkus.panache.common.Sort;

@Path("/rest")
public class RestResource {

    @Inject
    @RestClient
    RdevClient rdevClient;

    @Path("talks")
    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TalkEntity> hello() {
        return TalkEntity.listAll();
    }

    @Path("speakers")
    @Transactional
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpeakerEntity> speakers() {
        return SpeakerEntity.listAll(Sort.by("name"));
    }

    @Transactional
    @Path("import")
    @POST
    public String importData() {
        TalkEntity.deleteAll();
        for (TalkTransfer talkTransfer : rdevClient.talks()) {
            TalkEntity talkEntity = new TalkEntity();
            talkEntity.title = talkTransfer.name;
            talkEntity.description = talkTransfer.description;

            for (SpeakerTransfer speakerTransfer : talkTransfer.speakers) {
                SpeakerEntity speakerEntity = SpeakerEntity.find("name", speakerTransfer.name).firstResult();
                if(speakerEntity == null) {
                    speakerEntity = new SpeakerEntity();
                    speakerEntity.name = speakerTransfer.name;
                    speakerEntity.biography = speakerTransfer.description;
                    speakerEntity.company = speakerTransfer.company;
                    speakerEntity.avatar = speakerTransfer.avatar;
                }
                talkEntity.speakers.add(speakerEntity);
            }

            talkEntity.persist();
        }
        return String.format("Imported %s talks and %s speakers\n", TalkEntity.count(), SpeakerEntity.count());
    }
}