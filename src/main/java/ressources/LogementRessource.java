//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package ressources;

import entities.Logement;

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

import metiers.LogementBusiness;

@Path("/logements")
public class LogementRessource {
    public static LogementBusiness logementMetier = new LogementBusiness();

    public LogementRessource() {
    }

    @POST
    @Consumes("application/json")
    public Response addLogement(Logement l) {
        return logementMetier.addLogement(l) ? Response.status(Status.CREATED).build() : Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Produces({"application/json"})
    public Response getLogements(@QueryParam("delegation") String delegation, @QueryParam("reference") String reference) {
        new ArrayList();
        List liste;
        if (reference == null && delegation != null) {
            liste = logementMetier.getLogementsByDeleguation(delegation);
        } else if (delegation == null && reference != null) {
            liste = logementMetier.getLogementsListeByref(Integer.parseInt(reference));
        } else {
            liste = logementMetier.getLogements();
        }

        return liste.size() == 0 ? Response.status(Status.NOT_FOUND).build() : Response.status(Status.OK).entity(liste).build();
    }

    @PUT
    @Consumes({"application/xml"})
    @Path("{id}")
    public Response updateLogement(Logement updatedLogement, @PathParam("id") int reference) {
        return logementMetier.updateLogement(reference, updatedLogement) ? Response.status(Status.OK).build() : Response.status(Status.NOT_FOUND).build();
    }

    @Path("{id}")
    @DELETE
    public Response deleteLogement(@PathParam("id") int reference) {
        return logementMetier.deleteLogement(reference) ? Response.status(Status.OK).build() : Response.status(Status.NOT_FOUND).build();
    }
}