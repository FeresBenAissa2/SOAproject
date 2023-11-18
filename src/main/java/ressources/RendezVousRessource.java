//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ressources;

import entities.RendezVous;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import metiers.RendezVousBusiness;

@Path("rendezvous")
public class RendezVousRessource {
    public static RendezVousBusiness rendezVousMetier = new RendezVousBusiness();

    public RendezVousRessource() {
    }

    @POST
    @Consumes({"application/json"})
    public Response addrendezVous(RendezVous r) {
        return rendezVousMetier.addRendezVous(r) ? Response.status(Status.CREATED).build() : Response.status(Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Produces({"application/json"})
    public Response getRendezVous(@QueryParam("refLogement") String refLogement) {
        new ArrayList();
        List liste;
        if (refLogement != null) {
            liste = rendezVousMetier.getListeRendezVousByLogementReference(Integer.parseInt(refLogement));
        } else {
            liste = rendezVousMetier.getListeRendezVous();
        }

        return liste.size() == 0 ? Response.status(Status.NOT_FOUND).build() : Response.status(Status.OK).entity(liste).build();
    }

    @PUT
    @Consumes({"application/json"})
    @Path("{id}")
    public Response updateRdv(RendezVous updatedRendezVous, @PathParam("id") int id) {
        return rendezVousMetier.updateRendezVous(id, updatedRendezVous) ? Response.status(Status.OK).build() : Response.status(Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteRendezVous(@PathParam("id") int id) {
        return rendezVousMetier.deleteRendezVous(id) ? Response.status(Status.OK).build() : Response.status(Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @GET
    public Response getRendezVousbyId(@PathParam("id") int id) {
        return rendezVousMetier.getRendezVousById(id) != null ? Response.status(Status.OK).entity(rendezVousMetier.getRendezVousById(id)).build() : Response.status(Status.NOT_FOUND).build();
    }
}